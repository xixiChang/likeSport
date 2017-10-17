package ccc.tcl.com.sprotappui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.BuildConfig;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.customui.ToolBar;
import ccc.tcl.com.sprotappui.data.UpdateUser;
import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.db.SQLParaWrapper;
import ccc.tcl.com.sprotappui.db.SQLStatement;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.FileUploadPresenter;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.UserPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import ccc.tcl.com.sprotappui.utils.PictureCutUtil;
import ccc.tcl.com.sprotappui.utils.Util;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChangeUserActivity extends BaseActivity implements View.OnClickListener{
    /**
     * View
     */
    private ToolBar toolBar;
    private CircleImageView head;
    private TextView tId, tName,tRetain;
    private Button updateButton;
    private ActionSheetDialog pick_picture;

    private RelativeLayout nameLayout, signLayout;

    private AlertDialog alertDialog;

    /**
     * Data
     */
    private final int PHOTO_REQUEST_CUT = 0;
    private final int PHOTO_REQUEST_GALLERY = 1;
    private final int PHOTO_REQUEST_TAKE = 2;

    private final int Request_Permission_CAMERA = 1695;
    private final int Request_Permission_Write = 1696;
    private final int Request_Permission_Read = 1697;
    private String image_url, name, retain, uid;


    private File file;
    private String filename = System.currentTimeMillis() + ".jpg";
    private Uri outputFileUri;
    private Uri resultUri;

    private PictureCutUtil pictureCutUtil;
    private String imagePath = "storage/emulated/0/sportImage.jpg";

    private UserPresenter userPresenter = new UserPresenter();
    private UserPresenter userPresenterUpload = new UserPresenter();
    private FileUploadPresenter fileUploadPresenter = new FileUploadPresenter();

    private SportAppView<ResponseResult<UserInfo>> infoDownload = new SportAppView<ResponseResult<UserInfo>>() {
        @Override
        public void onSuccess(ResponseResult<UserInfo> response) {
            if (response.isSuccess()){

            }
            else {
                Toast.makeText(ChangeUserActivity.this, "获取信息失败:" + response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(ChangeUserActivity.this, "获取信息失败:" + msg, Toast.LENGTH_SHORT).show();
        }
    };

    private SportAppView<ResponseResult<String>> fileUpload = new SportAppView<ResponseResult<String>>() {
        @Override
        public void onSuccess(ResponseResult<String> response) {
            if (response.isSuccess()){
                image_url = response.getResult();
                uploadUserInfo();
            }
            else {
                Toast.makeText(ChangeUserActivity.this, "头像上传失败:" + response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(ChangeUserActivity.this, "头像上传失败:" + msg, Toast.LENGTH_SHORT).show();
        }
    };

    private SportAppView<ResponseResult<String>> infoUpload = new SportAppView<ResponseResult<String>>() {
        @Override
        public void onSuccess(ResponseResult<String> response) {
            dismissDialog();
            if (response.isSuccess()){
                saveUserInfo();
                Toast.makeText(ChangeUserActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(ChangeUserActivity.this, "用户信息上传失败:" + response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(ChangeUserActivity.this, "用户信息上传失败:" + msg, Toast.LENGTH_SHORT).show();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        super.setToolBar(toolBar, R.string.user_info, true);

        initView();
        resultUri =  Uri.parse(imagePath);
        pictureCutUtil = new PictureCutUtil(this);
    }

    private void initView() {
        nameLayout = (RelativeLayout) findViewById(R.id.sign_layout);
        signLayout = (RelativeLayout) findViewById(R.id.name_layout);

        head = (CircleImageView) findViewById(R.id.user_info_head);
        tId = (TextView) findViewById(R.id.user_info_id);
        tName = (TextView) findViewById(R.id.user_info_name);
        tRetain = (TextView) findViewById(R.id.user_info_retain);
        updateButton = (Button) findViewById(R.id.save_info);

        head.setOnClickListener(this);
        signLayout.setOnClickListener(this);
        nameLayout.setOnClickListener(this);
        updateButton.setOnClickListener(this);

        Glide.with(this).load(App.userInfo.getImage_url()).into(head);
        tId.setText(App.userInfo.getId());
        tName.setText(App.userInfo.getName());
        tRetain.setText(App.userInfo.getRetain());

        retain = App.userInfo.getRetain();
        name = App.userInfo.getName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userPresenter.onCreate();
        userPresenter.attachView(infoDownload);
        userPresenter.getUserInfo(App.userInfo.getId());

        fileUploadPresenter.onCreate();
        fileUploadPresenter.attachView(fileUpload);

        userPresenterUpload.onCreate();
        userPresenterUpload.attachView(infoUpload);
    }



    @Override
    protected void onStop() {
        userPresenter.onStop();
        userPresenterUpload.onStop();
        fileUploadPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_info:
                uploadUserHead();
                break;
            case R.id.user_info_head:
                popupWindow();
                break;
            case R.id.name_layout:
                modifyName();
                break;
            case R.id.sign_layout:
                Intent intent = new Intent(ChangeUserActivity.this, UserSignActivity.class);
                intent.putExtra("sign", retain);
                startActivityForResult(intent, 1759);
                break;
            default:
                break;
        }
    }

    private void modifyName() {
        View view = View.inflate(this, R.layout.dialog_change_name, null);
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("昵称")
                .setView(view)
                .create();

        alertDialog.show();

        final EditText nameEdit = (EditText) view.findViewById(R.id.name_edit);
        TextView commit = (TextView) view.findViewById(R.id.commit);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);

        nameEdit.setText(name);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString();
                if (!Util.isEmpty(name)){
                    name = name.trim();
                    tName.setText(name);
                }
                if (alertDialog != null && alertDialog.isShowing())
                    alertDialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null && alertDialog.isShowing())
                    alertDialog.dismiss();
            }
        });
    }

    private void uploadUserHead() {
        if (file == null || !file.exists()){
            Toast.makeText(this, "请设定一个头像吧", Toast.LENGTH_SHORT).show();
            return;
        }
        showProgressDialog(this, null, "正在设定");
        fileUploadPresenter.upLoadFile(file, "head");
    }


    private void uploadUserInfo() {

        /**
         * 赋值
         */
        retain = tRetain.getText().toString();
        name = tName.getText().toString();

        if (Util.isEmpty(name)){
            Toast.makeText(this, "用户昵称为空", Toast.LENGTH_SHORT).show();
            return;
        }

        UpdateUser updateUser = new UpdateUser();
        updateUser.setRetain(retain);
        updateUser.setImage_url(image_url);
        updateUser.setName(name);
        updateUser.setUser_id(App.userInfo.getId());
        userPresenterUpload.updateUser(updateUser);
    }

    private void saveUserInfo() {
        App.userInfo.setName(name);
        App.userInfo.setImage_url(image_url);
        App.userInfo.setRetain(retain);

        /**
         * save info to local DB
         */
        SQLParaWrapper sqlWrapper = new SQLParaWrapper(this);
        sqlWrapper.sqLiteDatabase.execSQL(SQLStatement.UpdateUser,
                sqlWrapper.getUpdateUserStringArray(App.userInfo));
    }

    private void popupWindow() {
        if (pick_picture == null) {
            pick_picture = new ActionSheetDialog(this, new String[]{"拍照", "从相册中选择"}, null);
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

            case 1759:
                retain = data.getStringExtra("sign");
                if (retain != null){
                    retain = retain.trim();
                    tRetain.setText(retain.length() > 16 ? retain.substring(0, 15) + "..." : retain);
                }
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
        intent.putExtra("aspectX", 200);
        intent.putExtra("aspectY", 200);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
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
        intent.putExtra("aspectX", 200);
        intent.putExtra("aspectY", 200);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", false);
        File out = new File(imagePath);
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }



    private void setPicToView() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(resultUri.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap= BitmapFactory.decodeStream(fis);
        SaveBitmapToFile(bitmap);
        head.setImageBitmap(bitmap);
    }


    private void SaveBitmapToFile(Bitmap photo_data) {
        file = pictureCutUtil.cutPictureQuality(photo_data, "head");
        //imagePath = imageFile.getAbsolutePath();
    }
}