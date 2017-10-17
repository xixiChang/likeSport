package ccc.tcl.com.sprotappui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ccc.tcl.com.sprotappui.BuildConfig;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.utils.PictureCutUtil;

public class PickPictureActivity extends BaseActivity {
    private static final String TAG = "PickPictureActivity";

    private FrameLayout frameLayout;
    private ImageView imageView;
    private File file;
    private String filename = System.currentTimeMillis() + ".jpg";
    private Uri outputFileUri;
    private Uri resultUri;

    private PictureCutUtil pictureCutUtil;
    private String imagePath = "storage/emulated/0/sportImage.jpg";

    private ActionSheetDialog pick_picture;
    private final int PHOTO_REQUEST_CUT = 0;
    private final int PHOTO_REQUEST_GALLERY = 1;
    private final int PHOTO_REQUEST_TAKE = 2;


    private final int Request_Permission_CAMERA = 1695;
    private final int Request_Permission_Write = 1696;
    private final int Request_Permission_Read = 1697;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_picture);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.setToolBar(toolbar, "创建活动（1 / 3）", true);

        imageView = (ImageView) findViewById(R.id.imageView);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout) ;
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow();
            }
        });
        pictureCutUtil = new PictureCutUtil(this);

        initFile();
        hasRequestPermission = true;
        super.initPM(permissionResult);
    }

    private void initFile() {
        resultUri =  Uri.parse("storage/emulated/0/sportImage.jpg");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pick_picture_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("确定要放弃创建吗");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PickPictureActivity.this.finish();
                    }
                });
                builder.setNegativeButton("取消", null).create().show();
                break;
            case R.id.next:
//                if (imageView.getVisibility() == View.VISIBLE)
//                    break;
                Intent intent = new Intent(PickPictureActivity.this, CreateActivity.class);
                PlatFormActivity platFormActivity = new PlatFormActivity();
                platFormActivity.setImage_url(imagePath);
                platFormActivity.setPublish_user_id(platFormActivity.getUser_id());
                Bundle data = new Bundle();
                data.putParcelable("data", platFormActivity);
                intent.putExtras(data);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:
                if (data != null)
                    startPhotoZoom2(data.getData());
                break;
            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    setPicToView();
                    break;
                }
                break;
            case PHOTO_REQUEST_TAKE:
                startPhotoZoom1(outputFileUri);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @param uri
     * @param xSize
     * @param ySize
     */
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom1(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");

        Uri outPutUri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        intent.putExtra("noFaceDetection", false);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 800);
        intent.putExtra("aspectY", 400);
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 400);
        intent.putExtra("return-data", false);
        File out = new File(imagePath);
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom2(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 800);
        intent.putExtra("aspectY", 400);
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 400);
        intent.putExtra("return-data", false);
        File out = new File(imagePath);
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    private void setPicToView() {
        imageView.setVisibility(View.GONE);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(resultUri.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap= BitmapFactory.decodeStream(fis);
        SaveBitmapToFile(bitmap);
        frameLayout.setBackground(new BitmapDrawable(null, bitmap ));
    }





    private void PopupWindow() {
        if (pick_picture == null) {
            pick_picture = new ActionSheetDialog(PickPictureActivity.this, new String[]{"拍照", "从相册中选择"}, null);
            pick_picture.cancelText("取消")
                    .isTitleShow(false)
                    .create();
            pick_picture.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            startTakePhoto();
                            pick_picture.dismiss();
                            break;

                        case 1:
                            startPickPhoto();
                            pick_picture.dismiss();
                            break;
                    }
                }
            });

        }
        pick_picture.show();
    }

    private void startPickPhoto() {
        if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Request_Permission_Write);
            return;
        }

        if (!checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Request_Permission_Read);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }


    private void startTakePhoto() {
        if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Request_Permission_Write);
            return;
        }

        if (!checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Request_Permission_Read);
            return;
        }


        if (!checkPermission(Manifest.permission.CAMERA)) {
            requestPermission(Manifest.permission.CAMERA, Request_Permission_CAMERA);
            return;
        }

        file = new File(Environment.getExternalStorageDirectory(), filename);
        outputFileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, PHOTO_REQUEST_TAKE);
    }

    private void SaveBitmapToFile(Bitmap photo_data) {
        File imageFile = pictureCutUtil.cutPictureQuality(photo_data, "activity");
        //imagePath = imageFile.getAbsolutePath();
    }

    private BaseActivity.PermissionResult permissionResult = new PermissionResult() {
        @Override
        public void onGranted(String name, int code) {
            Log.d(TAG, "onGranted: ");
            if (code == Request_Permission_CAMERA)
                startTakePhoto();
        }

        @Override
        public void onDenied(int code) {
            Log.d(TAG, "onDenied: ");
            Toast.makeText(PickPictureActivity.this, "你怕是点错了吧", Toast.LENGTH_SHORT).show();
        }
    };
}
