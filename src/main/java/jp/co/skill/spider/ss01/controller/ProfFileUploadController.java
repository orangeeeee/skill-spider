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

	LinkedList<FileMeta> files = new LinkedList<FileMeta>();
	FileMeta fileMeta = null;

	private static final Logger logger = Logger.getLogger(ProfFileUploadController.class);

	/**
	 * プロフィールの画像をアップロードする。
	 * http://hmkcode.com/spring-mvc-jquery-file-upload-multiple-dragdrop-progress/
	 * @param request
	 * @param response
	 * @return ファイル情報？
	 */
	@RequestMapping(value = "/ss01/upload", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> upload(
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

               /*cleanすると消えるのであまり良くない方法だと思うが調べたのでメモに残す。
               	以下、デプロイ先にファイルを作成する方法
               	logger.debug("一時ファイル出力先:" + request.getServletContext().getRealPath("/temp/files/"));
               	String copyPath = request.getServletContext().getRealPath("/skill-spider/temp/files/");
                */
                // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
                		//copyPath + System.getProperty("file.separator")
                		"/temp/files/"
                		+ mpf.getOriginalFilename()));
                //TODO これどうかな
//                mpf.transferTo(new File(""));

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

	/**
	 * 一時保存ディレクトリを作成する。
	 */
	private void createTempDir() {

		logger.debug("upload.createTempDir str");

		final String FS = File.separator;
		//windowsの場合、"/"始まりは、"c:\"に作られる。
		File f = new File(FS+"temp"+FS+"files"); //　変数に入れたセパレータで区切

		//ディレクトリが無ければ作らない。
		boolean rst = f.mkdirs(); //ディレクトリ作成(既にディレクトリがあればfalse)
		System.out.println("結果："+rst);

		if(rst) {
			logger.debug("新規でディレクトリを作成した。");
		}

		String[] list = f.list();//指定ディレクトリ内のファイル名のリストを取得する

		logger.debug("upload.createTempDir end");

	}

}
