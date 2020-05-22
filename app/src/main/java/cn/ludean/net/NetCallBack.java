package cn.ludean.net;


import io.reactivex.disposables.Disposable;

/**
 * @author JWT
 */

public interface NetCallBack<T> {
    //关联Disposable
    void star(Disposable d);
    //成功的
    <T> void onSuccess(T t);
    //异常的
    void onError(Throwable throwable);
}
