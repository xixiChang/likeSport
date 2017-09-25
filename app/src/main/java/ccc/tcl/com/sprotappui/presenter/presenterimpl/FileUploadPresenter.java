package ccc.tcl.com.sprotappui.presenter.presenterimpl;

import java.io.File;

import ccc.tcl.com.sprotappui.internet.RequestFile;
import ccc.tcl.com.sprotappui.internet.requestImpl.RetrofitHelper;
import ccc.tcl.com.sprotappui.model.ResponseResult;
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

public class FileUploadPresenter implements Presenter {
    private RetrofitHelper helper = RetrofitHelper.getInstance();
    private RequestFile requestFile;

    private SportAppView<ResponseResult> appView;
    private Observable<ResponseResult> resultObservable;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate() {
        requestFile = helper.retrofit.create(RequestFile.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(SportAppView view) {
        this.appView = view;
    }

    public void upLoadFile(File file, String des){
        RequestBody rFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), rFile);
        RequestBody rDes = RequestBody.create(MediaType.parse("multipart/form-data"), des);
        resultObservable = requestFile.fileUpload(part, rDes);
        subscribeData();
    }

    private Subscription subscribeData() {
        return resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        appView.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseResult responseResult) {
                        if (responseResult != null) {
                            appView.onSuccess(responseResult);
                        }
                    }
                });
    }
}
