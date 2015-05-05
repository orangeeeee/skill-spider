package jp.co.skill.spider.ss01.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

import jp.co.skill.spider.model.FileMeta;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * プロフィール画像アップロード用<br>
 * １ユーザ、１画像まで
 *
 * @author yuuichi
 *
 */
@Controller
public class ProfFileUploadController {

	//複数アップロードしたときに、アップロード分記憶できる。
	LinkedList<FileMeta> files = new LinkedList<FileMeta>();

	FileMeta fileMeta = null;

	final String FS = File.separator;

	private static final Logger logger = Logger.getLogger(ProfFileUploadController.class);


	/**
	 * ☆★☆ 複数アップロードサンプル ☆★☆
	 * プロフィールの画像をアップロードする。
	 * http://hmkcode.com/spring-mvc-jquery-file-upload-multiple-dragdrop-progress/
	 * @param request
	 * @param response
	 * @return ファイル情報？
	 */
	@RequestMapping(value = "/ss01/upload", method = RequestMethod.POST)
	public @ResponseBody FileMeta upload(
			MultipartHttpServletRequest request, HttpServletResponse response) {

		logger.debug("upload");
		// ここではtemp領域に維持するだけ。
		// 完了画面でサーバーに格納する。

		// 1. get the files from the request object
		Iterator<String> itr = request.getFileNames();

		FileMeta fileMeta = new FileMeta();

		 //2. get each file
        if(itr.hasNext()){

        	MultipartFile mpf = request.getFile(itr.next());

            //2.2 if files > 10 remove the first from the list
            if(files.size() >= 10)
                files.pop();

            //2.3 create new fileMeta
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
            fileMeta.setFileType(mpf.getContentType());

            try {

            	/**
            	 * linux、windows注意点
            	 * http://qiita.com/asadasan/items/e541abb6024996488580
            	 */
            	//ディレクトリがない場合に作る。
            	this.createTempDir();

               fileMeta.setBytes(mpf.getBytes());

                // copy file to local disk
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
                		//copyPath + System.getProperty("file.separator")
                		"/temp/files/"
                		+ mpf.getOriginalFilename()));


           } catch (IOException e) {

        	   fileMeta.setUploadResult(false);
           }
            //2.4 add to files
            files.add(fileMeta);
        }

		return fileMeta;
	}
	/**
	 * 一時保存ディレクトリを作成する。
	 * 本来は重ならないIDをつけるとよい。<br>
	 * それをクライアントに返して、Formで持つ方法がいい。<br>
	 */
	private void createTempDir() {

		logger.debug("upload.createTempDir str");

		//windowsの場合、"/"始まりは、"c:\"に作られる。
		File f = new File(FS+"temp"+FS+"files");

		//ディレクトリが無ければ作らない。
		boolean rst = f.mkdirs(); //ディレクトリ作成(既にディレクトリがあればfalse)

		if(rst) {
			logger.debug("新規でディレクトリを作成した。");
		}

		logger.debug("upload.createTempDir end");

	}

	/**
	 * ☆★☆ 複数アップロードサンプル ☆★☆
	 * プロフィールの画像をアップロードする。
	 * http://hmkcode.com/spring-mvc-jquery-file-upload-multiple-dragdrop-progress/
	 * @param request
	 * @param response
	 * @return ファイル情報？
	 */
	@RequestMapping(value = "/ss01/uploads", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> uploads(
			MultipartHttpServletRequest request, HttpServletResponse response) {

		logger.debug("upload");
		// ここではtemp領域に維持するだけ。
		// 完了画面でサーバーに格納する。

		// 1. get the files from the request object
		Iterator<String> itr = request.getFileNames();


		 //2. get each file
        while(itr.hasNext()){

        	MultipartFile mpf = request.getFile(itr.next());
        	System.out.println(mpf.getOriginalFilename() + " uploaded!");
        	//2.1 get next MultipartFile
//            mpf = request.getFile(itr.next());
            System.out.println(mpf.getOriginalFilename() +" uploaded! "+files.size());


            //2.2 if files > 10 remove the first from the list
            if(files.size() >= 10)
                files.pop();

            //2.3 create new fileMeta
            fileMeta = new FileMeta();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
            fileMeta.setFileType(mpf.getContentType());

            try {

            	/**
            	 * linux、windows注意点
            	 * http://qiita.com/asadasan/items/e541abb6024996488580
            	 */
            	//ディレクトリがない場合に作る。
            	this.createTempDir();

               fileMeta.setBytes(mpf.getBytes());

                // copy file to local disk
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
                		//copyPath + System.getProperty("file.separator")
                		"/temp/files/"
                		+ mpf.getOriginalFilename()));


           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
               return null;
           }
            //2.4 add to files
            files.add(fileMeta);
        }
		return files;
	}
}
