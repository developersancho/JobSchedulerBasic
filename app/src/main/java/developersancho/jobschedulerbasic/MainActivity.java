package developersancho.jobschedulerbasic;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnCancel, btnScheduleJob;
    private JobScheduler mJobScheduler;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCancel = findViewById(R.id.btnCancel);
        btnScheduleJob = findViewById(R.id.btnScheduleJob);
        /*Bir iş zamanlamak için nesne oluşturduk.*/
        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        btnScheduleJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJob2();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopJob2();
            }
        });
    }

    private void stopJob2() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancelled");
    }

    private void startJob2() {
        ComponentName componentName = new ComponentName(this, MyJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

    private void stopJob1() {
        /* burda da tüm işler iptal ettim. Dilerseniz tek tek de işleri iptal edebilirsiniz.*/
        mJobScheduler.cancelAll();
    }

    private void startJob1() {
    /*Burada da bir iş inşaa süreci yazdım.
    Birinci  parametre ile iş ID belirtilir.Yani bu işin ID si "1" dir.
    ikinci parametrede de iş servisi (JobSchedulerService sınıfı) belirttim.Fakat buraya istediğiniz aktivite yi veya herhangi bir
     uygulamayı vs vs atayabilirsin. Mesela camere paket yolunu veya dila yolunu vs aklına gelen tüm işleri buraya atayabilirsin.*/
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(),
                MyJobSchedulerService.class.getName()));
        //"setPeriodic" metodu ile çalışma periyodu süresini belirttim
        builder.setPeriodic(1000);


        if (mJobScheduler.schedule(builder.build()) <= 0) {
            //İnşaa edilmiş işlerle ilgili bir şeyler ters giderse burada uyarı mesajı vb. kodlar  kullanabilirsiniz
            Toast.makeText(MainActivity.this, mJobScheduler.schedule(builder.build()), Toast.LENGTH_SHORT).show();
        }
    }
}
