package autonomous.automation.web.util;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Properties;

public class SFTPConnection {

    private Session session;
    private Channel channel;
    private ChannelSftp channelSftp;
    private String sftpHost;
    private String sftpUser;
    private String sftpPassword;
    private String sftpPort;
    private String remoteFolder;

    public void connectExec(String host, String user, String password, String command) {
        try {

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                }
                if (channel.isClosed()) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
            channel.disconnect();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Upload 1 local file to SFTP Server
     *
     * @param localFileName
     * @return
     * @throws IOException
     */
    public int uploadFileToFTP(String localFileName, String sftpHost, String sftpUser, String sftpPassword, String remoteFolder, String sftpPort) throws IOException {
        int result = 0;
        File f = new File(localFileName);
        FileInputStream fis = null;
        try {
            connectFileTransfer(sftpHost, sftpUser, sftpPassword, remoteFolder, sftpPort);
            fis = new FileInputStream(f);
            this.channelSftp.put(fis, f.getName());
            //this.channelSftp.getSession().disconnect();
            this.channelSftp.disconnect();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SftpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return result;
    }

    /**
     * Upload multiple files into SFTP server. Empty the destination dir before
     * uploading.
     *
     * @param fileNames
     * @return
     * @throws IOException
     */
    public int uploadMultiFilesToFTP(String[] fileNames, String sftpHost, String sftpUser, String sftpPassword, String remoteFolder, String sftpPort) throws IOException {
        int num = 0;
        try {

            connectFileTransfer(sftpHost, sftpUser, sftpPassword, remoteFolder, sftpPort);

            //check if current dir is not empty
            //this.channelSftp.cd("..");
            this.channelSftp.rm("*");
            //this.channelSftp.mkdir("input");
            //this.channelSftp.cd("input");

            for (String name : fileNames) {
                System.out.println("Filename: " + name);
                File f = new File(name);
                FileInputStream fis = null;
                fis = new FileInputStream(f);
                this.channelSftp.put(fis, f.getName());
                num++;
                //this.channelSftp.getSession().disconnect();
                if (fis != null) {
                    fis.close();
                }
            }
            this.channelSftp.disconnect();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SftpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return num;
    }

    /**
     * Connect to SFTP server and open a session
     */
    public void connectFileTransfer(String sftpHost, String sftpUser, String sftpPassword, String remoteFolder, String sftpPort) {

        this.sftpHost = sftpHost;
        this.sftpUser = sftpUser;
        this.sftpPassword = sftpPassword;
        this.remoteFolder = remoteFolder;
        this.sftpPort = sftpPort;
        try {
            JSch jsch = new JSch();
            this.session = jsch.getSession(sftpUser, sftpHost, Integer.parseInt(sftpPort));
            this.session.setPassword(sftpPassword);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            this.session.setConfig(config);
            if (!this.session.isConnected()) {
                this.session.connect();
            }
            this.channel = this.session.openChannel("sftp");
            this.channel.connect();
            this.channelSftp = (ChannelSftp) this.channel;
            this.channelSftp.cd(remoteFolder);
        } catch (JSchException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ChannelSftp getChannelSftp() {
        return channelSftp;
    }

    public void setChannelSftp(ChannelSftp channelSftp) {
        this.channelSftp = channelSftp;
    }

    public String getSftpHost() {
        return sftpHost;
    }

    public void setSftpHost(String sftpHost) {
        this.sftpHost = sftpHost;
    }

    public String getSftpUser() {
        return sftpUser;
    }

    public void setSftpUser(String sftpUser) {
        this.sftpUser = sftpUser;
    }

    public String getSftpPassword() {
        return sftpPassword;
    }

    public void setSftpPassword(String sftpPassword) {
        this.sftpPassword = sftpPassword;
    }

    public String getSftpPort() {
        return sftpPort;
    }

    public void setSftpPort(String sftpPort) {
        this.sftpPort = sftpPort;
    }

    public String getRemoteFolder() {
        return remoteFolder;
    }

    public void setRemoteFolder(String remoteFolder) {
        this.remoteFolder = remoteFolder;
    }
}
