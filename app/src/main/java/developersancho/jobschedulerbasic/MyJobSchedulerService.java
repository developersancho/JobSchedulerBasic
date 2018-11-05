package developersancho.jobschedulerbasic;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MyJobSchedulerService extends JobService {

    //Mesajı mJobHandler olarak tanımladığımız Handler sınıfında tuttuk
    private Handler mJobHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //Mesajı uyarı seklinde arayuzde gösterdik..
            Toast.makeText(getApplicationContext(), "JobService task running", Toast.LENGTH_SHORT).show();
            jobFinished((JobParameters) msg.obj, false);
            return true;
        }
    });

    //İşin işlevseliğininn başlatıldığı event
    @Override
    public boolean onStartJob(JobParameters params) {
        // Handler sınıfında tuttugumuz mesajı yani işi  gondererek işi baslattık
        mJobHandler.sendMessage(Message.obtain(mJobHandler, 1, params));
        return true;
    }

    //İşin işlevselliğini durduran event
    @Override
    public boolean onStopJob(JobParameters params) {
        //Handler sınıfından , Mesajı silerek işi durdurduk
        //Aldıgı parametre ilgili iş sürecinin ID'si dir.
        mJobHandler.removeMessages(1);
        return false;
    }
}
