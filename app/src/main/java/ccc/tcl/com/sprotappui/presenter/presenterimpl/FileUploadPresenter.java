package ccc.tcl.com.sprotappui.presenter.presenterimpl;

import java.io.File;

import ccc.tcl.com.sprotappui.internet.RequestFile;
import ccc.tcl.com.sprotappui.internet.requestImpl.RetrofitHelper;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.BasePresenter;
import ccc.tcl.com.sprotappui.presenter.Presenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by user on 17-9-25.
 */

public class FileUploadPresenter extends BasePresenter {
    private RequestFile requestFile;

    @Override
    public void onCreate() {
        super.onCreate();
        requestFile = helper.retrofit.create(RequestFile.class);
    }


    public void upLoadFile(File file, String des){
        RequestBody rFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), rFile);
        RequestBody rDes = RequestBody.create(MediaType.parse("multipart/form-data"), des);
        resultObservable = requestFile.fileUpload(part, rDes);
        subscribeData();
    }

}
