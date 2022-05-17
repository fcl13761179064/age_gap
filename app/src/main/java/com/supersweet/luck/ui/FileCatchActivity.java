
package com.supersweet.luck.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.supersweet.luck.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import static android.provider.ContactsContract.CommonDataKinds.StructuredName.SUFFIX;
import static android.provider.Telephony.Mms.Part.FILENAME;
import static com.lzy.okgo.model.Progress.FOLDER;

public class FileCatchActivity extends AppCompatActivity {


    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_catch);
        Button btnSingleWJson = findViewById(R.id.btnSingleWJson);
        Button btnSingleRJson = findViewById(R.id.btnSingleRJson);
        TextView ttvwJson = findViewById(R.id.ttvwJson);
        // 独立写Json文件
        btnSingleWJson.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                save2("eerwqrewqr ewqrewqrewqrewqrwefdsfkldsajfmdlsajfdsajfdslafjdsai");
                BufferedWriter bw = null;
                FileOutputStream fos = null;
                try {
                    String string = "pby 阿范德萨范德萨发地方撒的数据";
                    fos = openFileOutput("data", MODE_PRIVATE);
                    bw = new BufferedWriter(new OutputStreamWriter(fos));
                    bw.write(string);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bw != null) {
                        try {
                            bw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        });

        btnSingleRJson.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String load = load();
                ttvwJson.setText(load);
            }
        });

    }


    //外部存取数据
    private void save2(String content){
        verifyStoragePermissions(this);
        FileOutputStream fileOutputStream= null;
        try {
            //fileOutputStream = openFileOutput("test.txt",MODE_PRIVATE);
            File dir=new File(Environment.getExternalStorageState(),"Demo");
            if(!dir.exists()){
                dir.mkdirs();
            }
            File file=new File(dir,"test.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            //指定读取文件"data"
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);  //将数据放在Builder
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    /**
     * 保存对象
     *
     * @param ser
     * @param file
     * @throws IOException
     */
    public static boolean saveObject(Context context, Serializable ser,
                                     String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();

                fos.close();

            } catch (Exception e) {
            }
        }
    }


    //然后通过一个函数来申请
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    private void writeFile(StringBuilder sb) {
        String foldername = Environment.getExternalStorageDirectory().getPath()
                + FOLDER;
        File folder = new File(foldername);
        if (folder != null && !folder.exists()) {
            if (!folder.mkdir() && !folder.isDirectory()) {
                Log.d("ppppppppp", "Error: make dir failed!");
                return;
            }
        }

        String stringToWrite = sb.toString();
        String targetPath = foldername + FILENAME + SUFFIX;
        File targetFile = new File(targetPath);
        if (targetFile != null) {
            if (targetFile.exists()) {
                targetFile.delete();
            }

            OutputStreamWriter osw;
            try {
                osw = new OutputStreamWriter(
                        new FileOutputStream(targetFile), "utf-8");
                try {
                    osw.write(stringToWrite);
                    osw.flush();
                    osw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    private void writeAppend(String fileName, String requestUrlStr) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, true);
            writer.write(requestUrlStr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
