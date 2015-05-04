/**
 * ユーザ情報登録画面用
 */

// ボタン押下時処理（POST）
function submitPrc(btnName) {
	var contextPath = document.getElementById('contextPath').value;
	var oForm = document.getElementById('oForm');
	oForm.method = "post";
	oForm.action = contextPath + "/" + btnName;
	oForm.submit();
}

// jQuery記述エリア
$(function() {
	/**---------------------------------------------------------------------/
	 * イベント		発生するタイミング
	 * dragstart	ドラッグ開始時
	 * drag			ドラッグしている間
	 * dragend		ドラッグ終了時
	 * dragenter	ドラッグしている要素がドロップ領域に入ったとき
	 * dragover		ドラッグしている要素がドロップ領域にある間
	 * dragleave	ドラッグしている要素がドロップ領域から出たとき
	 * drop			ドラッグしている要素がドロップ領域にドロップされたとき
	 ------------------------------------------------------------------------*/
	/*
	//false を返してデフォルトの処理を実行しないようにする
	window.addEventListener('dragenter', function(event) {
		event.preventDefault();
	}, false);
	 */

	/*
	* preventDefault()：その要素のイベントをキャンセルする。
	* stopPropagation()：親要素への伝播をキャンセルする。
	* return false; を使うと、その要素のイベントも親要素への伝播も両方キャンセルする
	*/
	function blockEventPropagation(event) {
		event.stopPropagation();
		event.preventDefault();
	};


	function dragOverHandler(e) {
		/*
		 * ファイルアップロードエリアに
		 * ファイルがオーバーされたときのCSS追加
		 */
		$(this).addClass('fileover');
		blockEventPropagation(e);
	};
	function dragOutHandler(e) {
		/*
		 * ファイルアップロードエリアに
		 * ファイルがオーバーされたときのCSS削除
		 */
		$(this).removeClass('fileover');
		blockEventPropagation(e);
	};
	function dragenterHandler(e) {
		blockEventPropagation(e);
	};

	//ドラッグ要素の位置による制御
	$('#dragArea').on('dragover', dragOverHandler);
	$('#dragArea').on('dragleave', dragOutHandler);
	$('#dragArea').on('dragenter', dragenterHandler);
	/*
	 * ドロップ時のイベント
	 */
	document.getElementById('dragArea').addEventListener('drop',
			function(event) {

				$(this).removeClass('fileover');
				//input type="file"を入れ替える。
				$("#profUpdBtn").replaceWith('<input id="profUpdBtn" type="file" multiple="multiple" />');

				event.preventDefault();
				var dt = event.dataTransfer;
				// メッセージエリアのメッセージをクリア
				result = $('#result');
				result.html('');

				//TODO 今回は１ファイルのみ対応にする。
		        if(dt.files.length > 1) {
		        	alert("１ファイルのみ指定してください。");
		        	return false;
		        }

				// / ドロップされたファイルを順次送信
				for (var i = 0; i < dt.files.length; i++) {
					uploadFiles(dt.files[i], i);
				}

			}, false);

	/*
	 * ファイルアップロード
	 * ※複数対応のサンプルで書いておく。※
	 */
	function uploadFiles(file, num) {

		var f_id = 'filename_' + num;
		var p_id = 'progress_' + num;
		$('#result').append(
				'<div><span id="' + f_id + '">' + file.name
						+ '</span><progress id="' + p_id
						+ '" value="0" max="100">0%</progress></div>');

		// データを送信用する
		var fd = new FormData();
		fd.append("file", file, file.name);

		var contextPath = document.getElementById('contextPath').value;
		var action_uri = contextPath + '/ss01\/upload';

		/*
		 * jQuery ajax参考サイト
		 * http://js.studio-kingdom.com/jquery/ajax/ajax
		 */
		$.ajax({
			xhr : function() {
				XHR = $.ajaxSettings.xhr();
				if (XHR.upload) {
					XHR.upload.addEventListener('progress', function(e) {
						progre = parseInt(e.loaded / e.total * 10000) / 100;
						console.log(progre + "%");
						document.getElementById(p_id).value = progre;
						document.getElementById(p_id).innerHTML = progre + '%';
					}, false);
				}
				return XHR;
			},
			url:action_uri,
			method : 'POST',
			data : fd,
			dataType : 'json',
			processData: false,
			contentType: false
			}).done(function( data ) {
		        // ...
			})
			.fail(function( data ) {
			        // ...
			})
			.always(function( data ) {
			        // ...
			});

/* 旧バージョンの記載方法
		// ajaxを使用してアップロード
		$.ajax(action_uri, {
			xhr : function() {
				XHR = $.ajaxSettings.xhr();
				if (XHR.upload) {
					XHR.upload.addEventListener('progress', function(e) {
						progre = parseInt(e.loaded / e.total * 10000) / 100;
						console.log(progre + "%");
						document.getElementById(p_id).value = progre;
						document.getElementById(p_id).innerHTML = progre + '%';
					}, false);
				}
				return XHR;
			},
			method : 'POST',
			contentType : false,
			processData : false,
			data : fd,
			dataType : 'json',
			success : function(data) {
				var msg = "成功";
				if (data['FLG'] == false) {
					msg = "失敗";
				}
				document.getElementById(f_id).innerHTML ="<br>" + file.name + ":" + msg
			}

		});

		*/
	};
    // ファイル選択フォームからの入力
    $("#profUpdBtn").bind("change", function () {

        // 選択されたファイル情報を取得
        var files = this.files;

        //TODO 今回は１ファイルのみ対応にする。
        if(files.length > 1) {
        	alert("１ファイルのみ指定してください。");
        	return false;
        }

        if(document.getElementById("filename_0") != null) {
        	$("#filename_0").parent("div").remove();
        }

		// ドロップされたファイルを順次送信
		for (var i = 0; i < files.length; i++) {
			uploadFiles(files[i], i);
		}
    });



    /*
	 *
	 * processData 型：Boolean 初期値：true
	 * デフォルトでは、dataオプションにオブジェクトとして渡されるデータ(厳密に言えば、文字列以外のもの)は、
	 * デフォルトのcontent-typeである"application/x-www-form-urlencoded"に合わせた形式でクエリー文字列へ変換されます。
	 * もしDOMDocument、またはその他の形式のデータを送信したい場合は、このオプションをfalseに設定します。
	 */
});