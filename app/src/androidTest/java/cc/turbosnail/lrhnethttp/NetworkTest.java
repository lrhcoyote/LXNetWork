package cc.turbosnail.lrhnethttp;


import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.concurrent.CountDownLatch;
import cc.turbosnail.LXBind;
import cc.turbosnail.lrhlibrary.BaseObserver;
import cc.turbosnail.lrhlibrary.net.HttpClient;
import cc.turbosnail.lrhnethttp.api.BingApi;
import cc.turbosnail.lrhnethttp.mvp.contract.BingContract;
import cc.turbosnail.lrhnethttp.mvp.contract.MedicalGuideContract;
import cc.turbosnail.lrhnethttp.network.MedicalGuideAppHandler;
import cc.turbosnail.lrhnethttp.network.MedicalGuideNetwork;
import cc.turbosnail.lrhnethttp.network.TestNetWorkAdapter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class NetworkTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("cc.turbosnail.xpqnethttp", appContext.getPackageName());
    }

    private static final String TAG = "ExampleInstrumentedTest";

    @Test
    public void bingTest(){
        System.out.println("测试开始: ");
        final CountDownLatch latch = new CountDownLatch(1);
        HttpClient httpClient = new HttpClient();
        httpClient.setAdapter(new TestNetWorkAdapter());
        httpClient.createService(BingApi.class)
                .bingIndex()
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String str) {
                        System.out.println("onSuccess: " + str);
                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onSuccess: " + e.getMessage());
                        latch.countDown();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
//        BingContract.Model mBindModel = LXBind.bind(BingContract.Model.class);
////        MedicalGuideContract.Model model = LXBind.bind(MedicalGuideContract.Model.class);
//        //测试MedicalGuideModel实现的方法
//        mBindModel.bingIndex(new BaseObserver<String>() {
//            @Override
//            public void onSuccess(String str) {
//                System.out.println("onSuccess: " + str);
//                latch.countDown();
//            }
//
//            @Override
//            public void onFailure(Throwable e) {
//                System.out.println("onFailure: " + e.getMessage());
//                latch.countDown();
//            }
//        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试结束: " + Thread.currentThread());
    }

    @Test
    public void medicalGuideTest(){
        MedicalGuideContract.Model model = LXBind.bind(MedicalGuideContract.Model.class);
        JSONObject request = new JSONObject();
        try {
            request.put("categoryId", "");
            request.put("typeId", 0);
            request.put("pageIndex", 0);
            request.put("pageSize", 10);
        }catch (JSONException e){
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(request.toString(), MediaType.parse("json/application"));
        final CountDownLatch latch = new CountDownLatch(1);
        model.getGuideListByCategoryId(requestBody, new BaseObserver<String>() {
            @Override
            public void onSuccess(String str) {
                System.out.println(str);
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable e) {
                System.out.println(e.getMessage());
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}