import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;

public class DecryptorThread extends Thread {
    private GUIForm form;
    private File file;
    private String password;
    private boolean isErrors;
    private String outPath;

    public DecryptorThread(GUIForm form) {
        this.form = form;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void run() {
        onStart();
        try {
            outPath = getOutPath();
            ZipFile zipFile = new ZipFile(file);
            zipFile.setPassword(password.toCharArray());
            zipFile.extractAll(outPath);
        } catch (Exception ex) {
            if (ex.getMessage().contains("Wrong Password")) {
                form.showWarning("Неверный пароль");
                isErrors = true;
                deleteFileOrDirectory(outPath);
            } else {
                form.showWarning(ex.getMessage());
            }
        }
        onFinish();
    }

    private void onStart() {
        form.setButtonsEnabled(false);
    }

    private void onFinish() {
        form.setButtonsEnabled(true);
        if (!isErrors) {
            form.showFinished();
        }
    }

    private void deleteFileOrDirectory(String path) {
        File fileOrDirectory = new File(path);
        if (fileOrDirectory.isDirectory()) {
            deleteDirectory(fileOrDirectory);
        } else {
            fileOrDirectory.delete();
        }
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    private String getOutPath() {
        String path = file.getAbsolutePath()
                .replaceAll("\\.enc$", "");
        for (int i = 1; ; i++) {
            String number = i > 1 ? Integer.toString(i) : "";
            String outPath = path + number;
            if (!new File(path + number).exists()) {
                return outPath;
            }
        }
    }
}
