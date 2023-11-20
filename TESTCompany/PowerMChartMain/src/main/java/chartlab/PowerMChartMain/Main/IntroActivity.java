package chartlab.PowerMChartMain.Main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import chartlab.PowerMChartEngine.KernelCore.GlobalUtils;
import chartlab.PowerMChartMain.Main.R;
import chartlab.PowerMChartApp.Dialog.ChartSupport.ProgressDlg;
import chartlab.PowerMChartApp.Widget.ListAdapt.GraphicsItemAdapter;
import chartlab.PowerMChartEngine.KernelCore.GlobalDirFile;
import chartlab.PowerMChartEngine.KernelCore.GlobalDefines;
import chartlab.PowerMChartEngine.Util.ZipManager;
import chartlab.PowerMChartMain.Utils.ThreadTask;

import static chartlab.PowerMChartEngine.KernelCore.GlobalEnums.GE_OBJPRICETYPENAMES;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class IntroActivity extends Activity
{
    public static final String FrameTypeKey = "PowerGraphicsType";
    public static final String ChartTypeMulti = "MultiFragmentApp";
    public static final String ChartTypeCompare = "CompareFragmentApp";
    public static final String ChartTypeForm = "FormChartFragmentApp";
    public static final String GraphTypeForm = "FormGraphFragmentApp";
    public static final String GraphicsSample1 = "PowerGraphicsSample1";

    private static final int REQUEST_PERMISSION_CODE = 1000;

    private long m_lLastClickTime = 0;
    private Context m_contIntro = null;

    private Button m_btnMulti;
    private Button m_btnCompare;
    private Button m_btnChartInfoInit;

    private int m_nGraphicsSampleStart = -1;
    private int m_nGraphMixStart = -1;
    private ListView m_lvGraphics;
    private List<String> m_strGraphicsNameList;

    private int m_nWritePermissionCheck = -1;
    private int m_nReadPermissionCheck = -1;

    private View m_vwIntro = null;

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        m_vwIntro = getLayoutInflater().inflate(R.layout.activity_intro, null);
        setContentView(m_vwIntro);

        InitIntroActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        m_vwIntro = getLayoutInflater().inflate(R.layout.activity_intro, null);
        //setContentView(R.layout.activity_powermchart_intro);
        setContentView(m_vwIntro);

        m_contIntro = this;

        //2016/12/28-강민석 6.0 Marshmallow (SDK 23↑) 권한 요청 처리
        CheckPermission();

        if (m_nWritePermissionCheck != PackageManager.PERMISSION_GRANTED ||
            m_nReadPermissionCheck != PackageManager.PERMISSION_GRANTED) return;

        File fileChartInfoDir = new File(GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_CHARTINFO_DIRECTORY);

        boolean bRunSetup = false;
        if(fileChartInfoDir.exists() == false)
        {
            ProgressDlg dlgProgress = new ProgressDlg(m_contIntro);
            dlgProgress.SetMessageTextColor(Color.WHITE);
            dlgProgress.SetMessage("설치후 차트 초기정보(DB,Data등) Setup중..");
            dlgProgress.show();

            RunUnZipChartInfo();

            dlgProgress.dismiss();
            bRunSetup = true;
        }

        InitIntroActivity();

        if(bRunSetup == true) Toast.makeText(m_contIntro,"설치완료",Toast.LENGTH_LONG).show();
    }

    private void InitIntroActivity()
    {
        m_btnMulti = (Button) findViewById(R.id.Btn_Multi);
        m_btnCompare = (Button) findViewById(R.id.Btn_Compare);

        m_btnChartInfoInit = (Button) findViewById(R.id.Btn_ChartInfoInit);

        m_strGraphicsNameList = new ArrayList<String>();
        m_strGraphicsNameList.add("FormChart-1");
        m_strGraphicsNameList.add("FormChart-2");
        m_strGraphicsNameList.add("FormChart-3");
        m_strGraphicsNameList.add("FormChart-4");

        m_strGraphicsNameList.add("Graph-Candle");
        m_strGraphicsNameList.add("Graph-Line");
        m_strGraphicsNameList.add("Graph-Mountain");
        m_strGraphicsNameList.add("Graph-Flow");
        m_strGraphicsNameList.add("Graph-Stair");
        m_strGraphicsNameList.add("Graph-Scatter");

        for(int i=16;i<GE_OBJPRICETYPENAMES.length;i++)
            m_strGraphicsNameList.add("Graph-" + GE_OBJPRICETYPENAMES[i]);

        m_nGraphMixStart = m_strGraphicsNameList.size();
        //Mix
        m_strGraphicsNameList.add("Mix-OscBarLine");
        m_strGraphicsNameList.add("Mix-MultiLineDots");
        m_strGraphicsNameList.add("Mix-StickBar_Stair");
        m_strGraphicsNameList.add("Mix-GroupBar_LineDots");
        m_strGraphicsNameList.add("Mix-2SideBar_LineDot");
        m_strGraphicsNameList.add("Mix-HorzBar_Mountain");
        m_strGraphicsNameList.add("Mix-StackBar_OSCBar");

        /////////////////////////////////////////////////////////////
        //Sample1
        m_nGraphicsSampleStart = m_strGraphicsNameList.size();
        m_strGraphicsNameList.add("Sample-Graphics1");

        m_lvGraphics = findViewById(R.id.Lv_Graphics);
        GraphicsItemAdapter graphicsListAdapter = new GraphicsItemAdapter(this,R.layout.listviewadapt_graphics_item, m_strGraphicsNameList, m_lvGraphics);
        m_lvGraphics.setAdapter(graphicsListAdapter);
        m_lvGraphics.setOnItemClickListener(mListItemClickListener);

        m_btnMulti.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Double Click후 창 두개 뜨는것 방지 1
                if(BaseFragmentActivity.IsActivieAlive()) return;

                // mis-clicking prevention, using threshold of 1000 ms(Double Click후 창 두개 뜨는것 방지)
                if (SystemClock.elapsedRealtime() - m_lLastClickTime < 1000) return;

                m_lLastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(m_contIntro, BaseFragmentActivity.class);
                intent.putExtra(FrameTypeKey, ChartTypeMulti);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        m_btnCompare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Double Click후 창 두개 뜨는것 방지 1
                if(BaseFragmentActivity.IsActivieAlive()) return;

                // mis-clicking prevention, using thresh    old of 1000 ms(Double Click후 창 두개 뜨는것 방지)
                if (SystemClock.elapsedRealtime() - m_lLastClickTime < 1000) return;

                m_lLastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(m_contIntro, BaseFragmentActivity.class);
                intent.putExtra(FrameTypeKey, ChartTypeCompare);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        m_btnChartInfoInit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // mis-clicking prevention, using threshold of 1000 ms(Double Click후 창 두개 뜨는것 방지)
                if (SystemClock.elapsedRealtime() - m_lLastClickTime < 1000) return;

                m_lLastClickTime = SystemClock.elapsedRealtime();

                RunUnZipChartInfo();
            }
        });
    }

    private void RunUnZipChartInfo()
    {
        new ThreadTask<Integer, Integer>()
        {
            private ProgressDlg dlgProgress = null;
            @Override
            protected void onPreExecute() {
                dlgProgress = new ProgressDlg(m_contIntro);
                if(dlgProgress != null && dlgProgress.isShowing() == false)
                {
                    dlgProgress.SetMessageTextColor(Color.WHITE);
                    dlgProgress.SetMessage("차트 초기정보 로드");
                    dlgProgress.show();
                }
            }

            @Override
            protected void doInBackground() {
                if(UnZipChartInfo() == false)
                    Toast.makeText(m_contIntro, "차트 데이터 압축 해체에 실패했습니다", Toast.LENGTH_SHORT).show();
                    //publishProgress();
            }

            @Override
            protected void onPostExecute() {
                if (dlgProgress != null) dlgProgress.dismiss();
            }

            @Override
            protected void onProgressUpdate()
            {
                Toast.makeText(m_contIntro, "차트 초기정보 파일을 찾을수 없습니다", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onCancelled()
            {
                if (dlgProgress != null) dlgProgress.dismiss();
            }
        }.execute();

        /*new AsyncTask<Void, Void, Void>()
        {
            private ProgressDlg dlgProgress = null;
            @Override
            protected void onCancelled()
            {
                if (dlgProgress != null) dlgProgress.dismiss();
                super.onCancelled();
            }

            @Override
            protected void onPreExecute()
            {
                dlgProgress = new ProgressDlg(m_contIntro);
                if(dlgProgress != null && dlgProgress.isShowing() == false)
                {
                    dlgProgress.SetMessageTextColor(Color.WHITE);
                    dlgProgress.SetMessage("차트 초기정보 로드");
                    dlgProgress.show();
                }
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(Void... values)
            {
                super.onProgressUpdate(values);
                Toast.makeText(m_contIntro, "차트 초기정보 파일을 찾을수 없습니다", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Void doInBackground(Void... params)
            {
                if(UnZipChartInfo() == false)
                    publishProgress();

                return null;
            }

            @Override
            protected void onPostExecute(Void result)
            {
                if (dlgProgress != null) dlgProgress.dismiss();

                super.onPostExecute(result);
            }
        }.execute();*/
    }

    private boolean UnZipChartInfo()
    {
        AssetManager assetManager = getAssets();
        try
        {
            String[] strFileName =  assetManager.list("ChartInfo");
            String strFileNameChartInfo = "chartinfo.zip";
            boolean bFind = false;
            assert strFileName != null;
            for(int i = 0; i<strFileName.length; i++)
            {
                if(strFileName[i].equals(strFileNameChartInfo))
                {
                    bFind = true;
                    break;
                }
            }

            if(bFind == false) return false;

            //2022/01/26 - 추가(폴더가 없는경우가 있다)
            GlobalDirFile.makeDirectory(GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_ROOT_DIRECTORY);
            //2022/01/26 - 추가(폴더가 없는경우가 있다)
            GlobalDirFile.makeDirectory(GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_ROOT_DIRECTORY+"ChartInfo/");
            //추가된 파일 목록은 여기에 추가해주세요
            //File fileResolutionProperty = new File(GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_ROOT_DIRECTORY +"ChartInfo/"+ "ResolutionProperty.xml");
            File fileChartInfo = new File(GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_ROOT_DIRECTORY + "chartinfo.zip");
            //if(!fileChartInfo.getParentFile().exists() && !fileResolutionProperty.exists())
            //    fileChartInfo.getParentFile().mkdirs();

            String strChartInfoZipPath = fileChartInfo.getPath();
            if (fileChartInfo.exists()) fileChartInfo.delete();

            //File fileChartInfoDir = new File(GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_CHARTINFO_DIRECTORY);
            //File fileChartInfoUserDir = new File(GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_CHARTINFO_USER_DIRECTORY);
            //if(fileChartInfoDir.isDirectory() && fileChartInfoDir.exists())
            //    GlobalDirFile.DeleteDir(fileChartInfoDir.getPath());

            //if(fileChartInfoUserDir.isDirectory() && fileChartInfoUserDir.exists())
            //    GlobalDirFile.DeleteDir(fileChartInfoUserDir.getPath());

            InputStream inputStream = assetManager.open("ChartInfo/"+strFileNameChartInfo);
            GlobalDirFile.copyFile(inputStream, strChartInfoZipPath);
            ZipManager.unZip(new File(GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_ROOT_DIRECTORY + "chartinfo.zip"), GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_ROOT_DIRECTORY);
            inputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    AdapterView.OnItemClickListener mListItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Double Click후 창 두개 뜨는것 방지 1
            if(BaseFragmentActivity.IsActivieAlive()) return;

            // mis-clicking prevention, using threshold of 1000 ms(Double Click후 창 두개 뜨는것 방지2)
            if (SystemClock.elapsedRealtime() - m_lLastClickTime < 1000) return;

            m_lLastClickTime = SystemClock.elapsedRealtime();

            if(position >= m_nGraphicsSampleStart)
            {
                Intent intent = new Intent(m_contIntro, BaseSample1FragmentActivity.class);
                intent.putExtra(FrameTypeKey, GraphicsSample1);
                intent.putExtra("MixGraphType", position - m_nGraphicsSampleStart);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
            else
            {
                Intent intent = new Intent(m_contIntro, BaseFragmentActivity.class);
                if (position < 4)
                {
                    intent.putExtra(FrameTypeKey, ChartTypeForm);
                    intent.putExtra("GraphicsType", position + "");
                }
                else
                {
                    if (position >= m_nGraphMixStart)
                    {
                        intent.putExtra(FrameTypeKey, GraphTypeForm);
                        intent.putExtra("MixGraphType", position - m_nGraphMixStart);
                    }
                    else if (position >= 4 && position <= 9)
                    {
                        intent.putExtra(FrameTypeKey, GraphTypeForm);
                        intent.putExtra("GraphicsType", position + "");
                    }
                    else
                    {
                        intent.putExtra(FrameTypeKey, GraphTypeForm);
                        intent.putExtra("GraphicsType", m_strGraphicsNameList.get(position));
                    }
                }
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }
    };

    private void CheckPermission()
    {
        //Marshmallow 이상 버전 Permission Check
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            m_nWritePermissionCheck = PackageManager.PERMISSION_GRANTED;
            m_nReadPermissionCheck = PackageManager.PERMISSION_GRANTED;
            return;
        }

        m_nWritePermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        m_nReadPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (m_nWritePermissionCheck != PackageManager.PERMISSION_GRANTED ||
             m_nReadPermissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_CODE); // define this constant yourself
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        // If request is cancelled, the result arrays are empty.
        //if(nPermissionSize >= 1) bPermissionPass = true;
        if (REQUEST_PERMISSION_CODE == requestCode)
        {
            boolean bPermissionPass = true;
            int nPermissionSize = permissions.length;
            for (int i = 0; i < nPermissionSize; i++)
            {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                {
                    bPermissionPass = false;
                    break;
                }
            }
            if (bPermissionPass == false)
            {
                Toast.makeText(m_contIntro, "권한 허용없이 임시데이터 로딩 불가능", Toast.LENGTH_LONG).show();

                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(500); //0.1초간 진동울림

                finish();
            }
            else
            {
                File fileChartInfoDir = new File(GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_CHARTINFO_DIRECTORY);
                boolean bRunSetup = false;
                if (fileChartInfoDir.exists() == false)
                {
                    ProgressDlg dlgProgress = new ProgressDlg(m_contIntro);
                    dlgProgress.SetMessageTextColor(Color.WHITE);
                    dlgProgress.SetMessage("설치후 차트 초기정보(DB,Data등) Setup중..");
                    dlgProgress.show();

                    RunUnZipChartInfo();

                    dlgProgress.dismiss();
                    bRunSetup = true;
                }

                InitIntroActivity();

                if (bRunSetup == true)
                    Toast.makeText(m_contIntro, "설치완료", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
