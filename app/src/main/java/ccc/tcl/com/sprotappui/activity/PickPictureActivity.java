package ccc.tcl.com.sprotappui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.PlatFromActivity;

public class PickPictureActivity extends BaseActivity{
    ImageView imageView;
    ImageView imageView2;
    private static final String TAG = "PickPictureActivity";
    private ActionSheetDialog pick_picture;
    final int PHOTO_REQUEST_CUT = 0;
    final int PHOTO_REQUEST_GALLERY = 1;
    final int PHOTO_REQUEST_TAKE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_picture);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow();
            }
        });
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 PopupWindow();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.setToolBar(toolbar, R.string.create_activity,true);
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/zz"  );
        if (!tmpDir.exists()){
            tmpDir.mkdir();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pick_picture_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("确定要放弃创建吗");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PickPictureActivity.this.finish();
                    }
                });
                builder.setNegativeButton("取消",null).create().show();
                break;
            case R.id.next:
                Intent intent = new Intent(PickPictureActivity.this,CreateActivity.class);
                PlatFromActivity platFromActivity = new PlatFromActivity();
                platFromActivity.setImage_url("image.url");
                Bundle data = new Bundle();
                data.putParcelable("data", platFromActivity);
                intent.putExtras(data);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:
                if (data != null)
                    startPhotoZoom(data.getData(), 0);
                break;
            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    setPicToView(data);
                    break;
                }
                break;
            case PHOTO_REQUEST_TAKE:
//                if(data == null){
//                    return;
//                }else{
                    //Bundle extras = data.getExtras();
                    //if (extras != null){
                        //获得拍的照片
                        //Bitmap bm = extras.getParcelable("data");
                        File fphoto = new File(Environment.getExternalStorageDirectory()+File.separator+"/zz/images1.jpg");
                        startPhotoZoom(Uri.fromFile(fphoto),0);
                    //}
                //}
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);

    }

    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {pick_picture.dismiss();
            Bitmap photo_data = bundle.getParcelable("data");
            SaveBitmapToFile(photo_data);
            Drawable drawable = new BitmapDrawable(photo_data);
            imageView2.setImageDrawable(drawable);

        }
    }

    private void PopupWindow(){
        if (pick_picture == null){
            pick_picture = new ActionSheetDialog(PickPictureActivity.this,new String[]{"拍照","从相册中选择"},null);
            pick_picture.cancelText("取消")
                    .isTitleShow(false)
                    .create();
            pick_picture.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0:
                            //构建隐式Intent
                            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            //更改系统相片存储路径
                            Uri photoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+File.separator+"/zz/images1.jpg"));
                            intent1.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                            //调用系统相机
                            startActivityForResult(intent1, PHOTO_REQUEST_TAKE);
                            pick_picture.dismiss();
                            break;

                        case 1:
                            Intent intent = new Intent(Intent.ACTION_PICK, null);
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                            startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                            pick_picture.dismiss();
                            break;
                    }
                }
            });

        }
        pick_picture.show();
    }
    private void SaveBitmapToFile(Bitmap photo_data) {
        File fphoto = new File(Environment.getExternalStorageDirectory()+"/zz/images1.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(fphoto);
            Log.d("", "SaveBitmapToFile: ");
            photo_data.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
