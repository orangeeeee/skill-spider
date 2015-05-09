/**
 * プロジェクト共通JavaScriptファイル
 */

/* Copyright (C) 2013-2015 Cybozu, Inc. */
if (typeof CB7 === "undefined") {
	throw new Error("std.js must be loaded before form.js")
}
if (typeof CB7.Form === "undefined") {
	(function() {
		var MD;
		CB7.Form = {
			AjaxPOST : {
				_sendForm : null,
				_handleInterval : null,
				_handlers : null,
				_windowScope : null,
				_Initial : function() {
					if (this._handleInterval) {
						clearInterval(this._handleInterval);
						this._handleInterval = null
					}
					if (this._sendForm) {
						var form = this._sendForm, elements = form.elements;
						if (form.target !== "_self") {
							form.target = "_self"
						}
						for (var i = 0; i < elements.length; ++i) {
							if (elements[i].id === "AjaxRequest") {
								elements[i].parentNode.removeChild(elements[i]);
								break
							}
						}
						this._sendForm = null
					}
				},
				_FrameLoad : function() {
					this._Initial();
					var iframe, iframedoc, iframeInner;
					this._windowScope.CB7.Spinner.Hide();
					this._windowScope.CB7.BlockUnload(false);
					iframe = document.getElementById("AjaxFrame");
					if (typeof iframe === "undefined") {
						return false
					}
					YAHOO.util.Event.removeListener(iframe, "load",
							this._FrameLoad);
					try {
						iframedoc = iframe.contentDocument
								|| iframe.contentWindow.document;
						if (typeof iframedoc === "undefined") {
							return false
						}
					} catch (e) {
						var handler = (this._handlers ? this._handlers.failure
								: null);
						if (handler) {
							var scope = (this._handlers.scope || window);
							handler.call(scope, "サーバーとの接続でエラーが発生しました")
						} else {
							alert("サーバーとの接続でエラーが発生しました。")
						}
						return false
					}
					iframeInner = iframedoc.body.innerHTML;
					if (typeof iframeInner === "undefined") {
						return false
					}
					var handler = null;
					var scope = null;
					if (CB7.IsErrorResponse(iframeInner)) {
						handler = (this._handlers ? this._handlers.failure
								: null);
						if (handler) {
							scope = (this._handlers.scope || window);
							handler.call(scope, iframeInner,
									this._handlers.argument)
						} else {
							this._windowScope.CB7.Dialog
									.ShowErrorResponse(iframeInner)
						}
						return false
					}
					handler = (this._handlers ? this._handlers.success : null);
					if (handler) {
						scope = (this._handlers.scope || window);
						handler.call(scope, iframeInner,
								this._handlers.argument);
						return true
					}
					var ajaxRedirectId = iframedoc
							.getElementById("AjaxRedirect");
					if (ajaxRedirectId) {
						var redirectLocation = "";
						if (iframeInner.match(/<!--\s*Location:/)) {
							redirectLocation = RegExp.rightContext;
							if (redirectLocation.match(/\s*-->/)) {
								redirectLocation = RegExp.leftContext
							}
						}
						redirectLocation = redirectLocation.split("&amp;")
								.join("&");
						var pos = redirectLocation.indexOf("#");
						if (pos !== -1) {
							var dummy = "&dummy="
									+ Math.floor(Math.random() * 1000);
							if (redirectLocation.charAt(pos - 1) === "&") {
								redirectLocation = redirectLocation.replace(
										"&#", dummy + "&#")
							} else {
								redirectLocation = redirectLocation.replace(
										"#", dummy + "#")
							}
						}
						var ua = navigator && navigator.userAgent;
						if (ua.match(/Firefox\//)
								|| (YAHOO.env.ua.android >= 3
										&& YAHOO.env.ua.android < 5
										&& ua.match(/AppleWebKit\//) && !ua
										.match(/Chrome\//))) {
							if (document.readyState === "complete") {
								location.replace(redirectLocation)
							} else {
								location.assign(redirectLocation)
							}
						} else {
							location.href = redirectLocation
						}
						return true
					}
					alert("予期しないエラーが発生しました。");
					return false
				},
				_IframeInterval : function() {
					this._handleInterval = setInterval(function() {
						try {
							var ajaxIframe = document
									.getElementById("AjaxFrame");
							ajaxIframe.contentWindow.document.body
						} catch (e) {
							CB7.Form.AjaxPOST._Initial();
							CB7.Spinner.Hide();
							alert("サーバーとの接続でエラーが発生しました。")
						}
					}, 1000)
				},
				SetEvent : function(form, script, handlers, windowScope) {
					if (this._sendForm) {
						return false
					}
					if (form.target && form.target !== "_self") {
						return true
					}
					if (document.getElementById("AjaxRequest")) {
						alert("予期しないエラーが発生しました。");
						return false
					}
					this._sendForm = form;
					if (typeof script !== "undefined") {
						if (false === eval(script)) {
							this._sendForm = null;
							return false
						}
					}
					this._handlers = handlers;
					this._windowScope = (windowScope || window);
					this._windowScope.CB7.Spinner.Show(null, "処理中…",
							CB7.Spinner.POST_DELAY);
					var el = document.createElement("input");
					el.type = "hidden";
					el.id = "AjaxRequest";
					el.name = "AjaxRequest";
					el.value = "AjaxRequest";
					form.appendChild(el);
					form.target = "AjaxFrame";
					var iframe = document.getElementById("AjaxFrame");
					YAHOO.util.Event.on(iframe, "load", this._FrameLoad, this,
							true);
					this._windowScope.CB7.BlockUnload(true);
					if (!YAHOO.env.ua.ie) {
						this._IframeInterval()
					}
					return true
				}
			},
			Submit : function(formname, values) {
				var form = document.forms[formname];
				if (!form) {
					return false
				}
				var i = 0;
				for ( var p in values) {
					if (values.hasOwnProperty(p)) {
						var el = form.elements[p];
						if (el) {
							if (el.tagName === "SELECT") {
								for (i = 0; i < el.options.length; ++i) {
									if (el.options[i].value === values[p]) {
										el.selectedIndex = i;
										break
									}
								}
							} else {
								el.value = values[p]
							}
						} else {
							el = document.createElement("INPUT");
							el.type = "hidden";
							el.name = p;
							el.value = values[p];
							form.appendChild(el)
						}
					}
				}
				for (i = 0; i < form.elements.length; ++i) {
					if (form.elements[i].name.toLowerCase() === "submit"
							|| form.elements[i].name.toLowerCase() === "modify") {
						form.elements[i].click()
					}
				}
			},
			ClickLimit : function(checkbox, year, month, day) {
				if (checkbox.checked) {
					checkbox.form.elements["LimitDate.Year"].disabled = true;
					checkbox.form.elements["LimitDate.Month"].disabled = true;
					checkbox.form.elements["LimitDate.Day"].disabled = true;
					InitDateSelect(checkbox.form.name, "LimitDate", true)
				} else {
					checkbox.form.elements["LimitDate.Year"].disabled = false;
					checkbox.form.elements["LimitDate.Month"].disabled = false;
					checkbox.form.elements["LimitDate.Day"].disabled = false;
					InitDateSelect(checkbox.form.name, "LimitDate", false,
							year, month, day)
				}
			},
			SimpleReply : function(confDisabled) {
				var simpleReplyEnable = $("#SimpleReplyEnable");
				var simpleReplyEnableLabel = $("#SimpleReplyEnableLabel");
				var simpleReplyNameSelect = $("#SimpleReplyNameSelect");
				var simpleReplyNameDirect = $("#SimpleReplyNameDirect");
				var simpleReplyNameOn = function() {
					simpleReplyNameSelect.prop("disabled", null);
					simpleReplyNameDirect.prop("disabled", null)
				};
				var simpleReplyNameOff = function() {
					simpleReplyNameSelect.prop("disabled", "disabled");
					simpleReplyNameDirect.prop("disabled", "disabled")
				};
				var simpleReplyEnableOn = function() {
					simpleReplyEnable.prop("checked", true);
					simpleReplyEnable.prop("disabled", null);
					simpleReplyEnableLabel.prop("class", null);
					simpleReplyEnableLabel.on("mouseover", function() {
						this.style.color = "#ff0000"
					});
					simpleReplyNameOn()
				};
				var simpleReplyEnableOff = function() {
					simpleReplyEnable.prop("checked", false);
					simpleReplyEnable.prop("disabled", "disabled");
					simpleReplyEnableLabel.prop("class", "fontDisable");
					simpleReplyEnableLabel.prop("onmouseover", null);
					simpleReplyEnableLabel.off("mouseover");
					simpleReplyNameOff()
				};
				var canFollow = $("#follow");
				if (canFollow.length > 0) {
					var onChangeCanFollow = function(isChecked) {
						if (isChecked) {
							simpleReplyEnableOn()
						} else {
							simpleReplyEnableOff()
						}
					};
					onChangeCanFollow(canFollow.prop("checked"));
					canFollow.change(function() {
						onChangeCanFollow(this.checked)
					})
				}
				var onChangeEnable = function(isChecked) {
					if (isChecked) {
						simpleReplyNameOn()
					} else {
						simpleReplyNameOff()
					}
				};
				simpleReplyNameSelect.change(function() {
					if (this.value === "") {
						simpleReplyNameDirect.show()
					} else {
						simpleReplyNameDirect.hide()
					}
				});
				onChangeEnable(simpleReplyEnable.prop("checked"));
				simpleReplyEnable.change(function() {
					onChangeEnable(this.checked)
				});
				if (confDisabled === "1") {
					simpleReplyEnable.prop("checked", false);
					simpleReplyNameOff()
				} else {
					simpleReplyEnable.prop("checked", true);
					simpleReplyNameOn()
				}
			},
			GetSimpleReplyRequestParams : function() {
				return "&__SimpleReplyEnable="
						+ $("#SimpleReplyEnable").prop("checked")
						+ "&__SimpleReplyNameSelect="
						+ encodeURIComponent($("#SimpleReplyNameSelect").val())
						+ "&__SimpleReplyNameDirect="
						+ encodeURIComponent($("#SimpleReplyNameDirect").val())
			},
			MultiDates : {
				dateValues : [],
				cssPickerSelected : "calendarPickerSelected",
				maxSelectableDates : 20,
				iframe : null,
				imgPath : null,
				load : function(id, imgPath, year, month, date) {
					MD.imgPath = imgPath;
					var dateValue = year * 10000 + month * 100 + date;
					MD.dateValues.push(dateValue);
					MD.addDateToSelectedDates(year, month, date);
					MD.iframe = $("#" + id);
					MD.iframe
							.load(function() {
								MD.updateCounter();
								$.each(MD.dateValues, function() {
									MD.iframe.contents().find(
											"#" + this.toString()).addClass(
											MD.cssPickerSelected)
								});
								$(".selectedDates").css(
										"height",
										$(".calendarPickerMultiFrame")
												.innerHeight());
								MD.iframe
										.contents()
										.find(
												".calendarPickerMultiSelectedMessage")
										.click(
												function() {
													$(
															".selectedDatesColumn, .vr_spaceColumn")
															.toggle();
													$(this)
															.find(
																	".columnToggleButton")
															.toggle()
												})
										.hover(
												function() {
													$(this)
															.addClass(
																	"calendarPickerMultiSelectedMessageHover")
												},
												function() {
													$(this)
															.removeClass(
																	"calendarPickerMultiSelectedMessageHover")
												});
								MD.iframe
										.contents()
										.find(".calendarPickerWeekdayRow th")
										.hover(
												function() {
													$(this)
															.addClass(
																	"calendarPickerWeekdayRowCellHover")
												},
												function() {
													$(this)
															.removeClass(
																	"calendarPickerWeekdayRowCellHover")
												})
							})
				},
				updateCounter : function() {
					if (MD.dateValues.length >= MD.maxSelectableDates) {
						MD.iframe.contents().find("#alert").text("(上限に達しました)")
					} else {
						MD.iframe.contents().find("#alert").empty()
					}
					MD.iframe.contents().find("#numOfSelectedDates").text(
							MD.dateValues.length)
				},
				selectedDateID : function(dateValue) {
					return "picked-" + dateValue.toString()
				},
				addDateToSelectedDates : function(year, month, date) {
					var dateObject = new Date(year, month - 1, date);
					dateObject.getDay();
					var dayLabel = new Array("(日)", "(月)", "(火)", "(水)", "(木)",
							"(金)", "(土)");
					var dateValue = year * 10000 + month * 100 + date;
					var id = MD.selectedDateID(dateValue);
					var html = '<div class="selectedDate clearfix" id ="'
							+ id
							+ '"><span class="selectedDateText"><input type="hidden" name="SetMultiDates" value="da.'
							+ year
							+ "."
							+ month
							+ "."
							+ date
							+ '">'
							+ year
							+ "年"
							+ month
							+ "月"
							+ date
							+ "日"
							+ dayLabel[dateObject.getDay()]
							+ '</span><img class="selectedDateDelete" src="'
							+ MD.imgPath
							+ '/delete16.png" align="absmiddle" border="0"  id ="delete'
							+ id + '"></div>';
					var selectedDates = $("#selectedDates");
					if (selectedDates.children().length === 0) {
						selectedDates.append(html)
					} else {
						var i = 0;
						selectedDates.children().each(function() {
							i++;
							if (id < this.id) {
								$(html).insertBefore(this);
								return false
							} else {
								if (i === selectedDates.children().length) {
									selectedDates.append(html)
								}
							}
						})
					}
					MD.bindOnClickUnselect(dateValue, id)
				},
				onClickDate : function(that, year, month, date) {
					var dateValue = year * 10000 + month * 100 + date;
					var id = MD.selectedDateID(dateValue);
					if ($(that).hasClass(MD.cssPickerSelected)) {
						$(that).removeClass(MD.cssPickerSelected);
						$("#" + id).remove();
						$.each(MD.dateValues, function(idx, val) {
							if (dateValue === val) {
								MD.dateValues.splice(idx, 1)
							}
						})
					} else {
						if (MD.dateValues.length < MD.maxSelectableDates) {
							MD.dateValues.push(dateValue);
							$(that).addClass(MD.cssPickerSelected);
							MD.addDateToSelectedDates(year, month, date)
						}
					}
					MD.updateCounter()
				},
				bindOnClickUnselect : function(dateValue, id) {
					$("#delete" + id).click(
							function() {
								var selectedPicker = MD.iframe.contents().find(
										"#" + dateValue.toString());
								if (selectedPicker.length > 0) {
									selectedPicker
											.removeClass(MD.cssPickerSelected)
								}
								$("#" + id.toString()).remove();
								$.each(MD.dateValues, function(idx, val) {
									if (dateValue === val) {
										MD.dateValues.splice(idx, 1)
									}
								});
								MD.updateCounter()
							})
				},
				onClickDayHeader : function(clickedClass) {
					var isAllSelected = true;
					var isAllUnSelected = true;
					var selectedDay = MD.iframe.contents().find(
							"." + clickedClass);
					selectedDay.each(function() {
						if ($(this).hasClass(MD.cssPickerSelected)) {
							isAllUnSelected = false
						} else {
							isAllSelected = false
						}
					});
					if (isAllSelected || isAllUnSelected) {
						selectedDay.each(function() {
							this.click()
						})
					} else {
						selectedDay.each(function() {
							if (!$(this).hasClass(MD.cssPickerSelected)) {
								this.click()
							}
						})
					}
				}
			},
			canDnD : function() {
				var div = document.createElement("div");
				return ("ondragstart" in div && "ondragover" in div && "ondrop" in div)
			},
			hasXHRProgress : function() {
				var req = window.XMLHttpRequest && new window.XMLHttpRequest();
				return "onprogress" in req
			},
			availableHtml5FileDnD : function() {
				var Form = CB7.Form;
				if (!window.FileReader || !window.FormData || !Form.canDnD()
						|| !Form.hasXHRProgress()) {
					return false
				}
				return true
			}
		};
		MD = CB7.Form.MultiDates;
		CB7.Form.OpenFileDialog = function(argformName, argApp, argForceAttach) {
			var forceAttach = "";
			if (argForceAttach) {
				forceAttach = "&Force=1"
			}
			this.url = "?page=FileDialog&FormName=" + argformName + "&App="
					+ argApp + forceAttach;
			this._name = "File";
			this._features = "width=600,height=400,resizable=yes,scrollbars=yes,status=yes,toolbar=no"
		};
		CB7.Form.OpenFileDialog.prototype = {
			open : function() {
				window.open(this.url, this._name, this._features)
			}
		};
		CB7.Form.MultiInputFile = function(options) {
			this.sfx = options.sfx;
			this.formFileArea = options.formFileArea;
			this.formName = options.formName;
			this.formKey = options.formKey;
			this.formFormName = options.formFormName;
			this.formHideMimeType = options.formHideMimeType;
			this.formApp = options.formApp;
			this.formForceAttach = options.formForceAttach;
			this._fileQueue = [];
			this._canceledFiles = {};
			this._uploading = false;
			this._totalNumOfFiles = 0
		};
		CB7.Form.MultiInputFile.DropAreaUI = function(argFormFileArea, argSfx) {
			this.sfx = argSfx;
			this.formFileArea = argFormFileArea;
			this._dragging = false;
			this._countOver = 0;
			this._checkPoint = 0;
			this._isChecking = false;
			this._timeoutID = null
		};
		CB7.Form.MultiInputFile.prototype = {
			stopDnDEvent : function(evt) {
				evt.stopPropagation();
				evt.preventDefault()
			},
			calcSize : function(bytes) {
				var localSize = 0;
				if (bytes < 1024) {
					return bytes + "Bytes"
				} else {
					if (bytes / 1024 < 1024) {
						return Math.ceil(bytes / 1024) + "KB"
					} else {
						if (bytes / (1024 * 1024) < 1024) {
							localSize = Math.ceil(bytes * 10 / (1024 * 1024));
							return localSize / 10 + "MB"
						} else {
							localSize = Math.ceil(bytes * 10
									/ (1024 * 1024 * 1024));
							return localSize / 10 + "GB"
						}
					}
				}
			},
			execute : function() {
				var mif = this;
				if (!CB7.Form.availableHtml5FileDnD()) {
					mif.formFileArea.find(".js-availableHtml5FileDnD").hide()
				} else {
					mif.formFileArea.find(".unavailableHtml5FileDnD").hide();
					var addDeleteButtonEvent = function(localSfx, optionValue) {
						$("#progressDelete" + localSfx).click(
								function() {
									$("#progressRow" + localSfx).remove();
									$(
											"#legacyFileSelect" + mif.sfx
													+ '> option[value="'
													+ optionValue + '"]')
											.remove();
									return false
								})
					};
					$("#legacyFileSelect" + mif.sfx + " option")
							.each(
									function() {
										var option = $(this);
										if (option.val() !== "") {
											var localSfx = mif.sfx + "-"
													+ mif._totalNumOfFiles;
											var html = $(
													"#js-template-attachedfile")
													.html();
											html = html.replace(/%ID_SUFFIX%/g,
													localSfx);
											html = html.replace(
													/%OPTION_HTML%/g, option
															.html());
											$("#progressContainer" + mif.sfx)
													.append(html);
											addDeleteButtonEvent(localSfx,
													option.val());
											mif._totalNumOfFiles++
										}
									});
					var fileInput = $("#files" + mif.sfx);
					var fileInputContainer = $("#fileInputContainer" + mif.sfx);
					var onInputChange = function(that) {
						uploadFiles(that.files);
						fileInput.val("");
						if (fileInput.val() !== "") {
							fileInputContainer
									.empty()
									.append(
											'<input type="file" id="files'
													+ mif.sfx
													+ '" name="files[]" size="0" multiple>');
							$("#files" + mif.sfx).change(function() {
								onInputChange(this)
							})
						}
					};
					fileInput.change(function() {
						onInputChange(this)
					});
					var dropAreaUI = new CB7.Form.MultiInputFile.DropAreaUI(
							mif.formFileArea, mif.sfx);
					dropAreaUI.initVals();
					$("body")[0].addEventListener("dragover", function(evt) {
						dropAreaUI.bodyDragover();
						mif.stopDnDEvent(evt);
						evt.dataTransfer.dropEffect = "none"
					});
					var dropArea = $("#dropArea" + mif.sfx)[0];
					dropArea.addEventListener("drop", function(evt) {
						dropAreaUI.dragAreaDrop();
						mif.stopDnDEvent(evt);
						uploadFiles(evt.dataTransfer.files)
					}, false);
					dropArea.addEventListener("dragover", function(evt) {
						dropAreaUI.dragAreaDragover();
						mif.stopDnDEvent(evt);
						evt.dataTransfer.dropEffect = "copy"
					}, false)
				}
				function uploadFiles(files) {
					for (var i = 0; i < files.length; i++) {
						uploadFile(files[i], mif._totalNumOfFiles + i);
						mif._fileQueue.push({
							file : files[i],
							j : mif._totalNumOfFiles + i
						})
					}
					mif._totalNumOfFiles += files.length;
					if (mif._uploading) {
						return
					}
					var obj;
					(function iterate() {
						mif._uploading = true;
						if (mif._fileQueue.length === 0) {
							mif._uploading = false;
							return
						}
						obj = mif._fileQueue.splice(0, 1)[0];
						var file = obj.file;
						var j = obj.j;
						var localSfx = mif.sfx + "-" + j;
						var xhr = new XMLHttpRequest();
						var fd = new FormData();
						fd.append(mif.formName, file);
						fd.append("csrf_ticket", mif.formKey);
						if (mif._canceledFiles[obj.j]) {
							mif._canceledFiles[obj.j] = null;
							iterate()
						} else {
							$("#progressCancel" + localSfx).click(function() {
								xhr.abort();
								return false
							});
							xhr.upload.addEventListener("progress", function(
									evt) {
								var prog = evt.loaded * 100 / evt.total;
								$("#progress" + localSfx).val(prog);
								$("#progressDesc" + localSfx).text(
										Math.ceil(prog) + "%")
							}, false);
							xhr
									.addEventListener(
											"load",
											function() {
												$("#progress" + localSfx)
														.hide();
												$("#progressDesc" + localSfx)
														.text("");
												if (CB7
														.IsErrorResponse(xhr.response)
														|| !xhr.responseText) {
													var mb = CB7.Dialog
															.ShowErrorResponse(xhr.response);
													var onError = function() {
														mb.hideEvent
																.unsubscribe(
																		onError,
																		mb);
														iterate();
														return
													};
													mb.hideEvent.subscribe(
															onError, mb);
													$(
															"#progressDesc"
																	+ localSfx)
															.text(
																	"ファイル添付に失敗しました。");
													return
												}
												var doc = document;
												var form = doc.forms[mif.formFormName];
												var legacyFileSelect = $(
														"#legacyFileSelect"
																+ mif.sfx).get(
														0);
												if (!form || !legacyFileSelect) {
													return false
												}
												var inner = "";
												var response = xhr.response;
												if (!response
														|| !(inner = response
																.match(/<span\s[^>]*>[^<]*<\/span>/i))) {
													return false
												}
												var dummy = doc
														.createElement("DIV");
												dummy.innerHTML = inner;
												var opt = doc
														.createElement("OPTION");
												opt.value = dummy.firstChild.id;
												opt.text = dummy.firstChild.innerHTML;
												var options = legacyFileSelect.options;
												options.add(opt,
														options.length - 1);
												dummy = null;
												$("#progressCancel" + localSfx)
														.hide();
												$("#progressDelete" + localSfx)
														.show();
												$("#progressTitle" + localSfx)
														.text(opt.text);
												$(
														"#progressRelation"
																+ localSfx)
														.attr(
																"name",
																"FILE_ID"
																		+ opt.value)
														.val(localSfx);
												addDeleteButtonEvent(localSfx,
														opt.value);
												iterate()
											}, false);
							xhr.addEventListener("error", function() {
								$("#progress" + localSfx).hide();
								$("#progressDesc" + localSfx).text(
										"アップロードに失敗しました。");
								$("#progressCancel" + localSfx).hide();
								$("#progressDelete" + localSfx).show();
								$("#progressDelete" + localSfx).click(
										function() {
											$("#progressRow" + localSfx)
													.remove();
											return false
										});
								iterate()
							}, false);
							xhr.addEventListener("abort", function() {
								$("#progressRow" + localSfx).remove();
								iterate()
							}, false);
							var hideMimeType = "";
							if (mif.formHideMimeType) {
								hideMimeType = "&mOff=1"
							}
							var forceAttach = "";
							if (mif.formForceAttach) {
								forceAttach = "&Force=1"
							}
							xhr.open("POST",
									"?page=InlineFile&notimecard=1&name="
											+ mif.formName + "&formname="
											+ mif.formFormName + "&app="
											+ mif.formApp + forceAttach
											+ hideMimeType + "&encoding=utf-8");
							xhr.send(fd)
						}
					})()
				}
				function uploadFile(file, j) {
					var localSfx = mif.sfx + "-" + j;
					var size = mif.calcSize(file.size);
					var html = $("#js-template-uploadedfile").html();
					html = html.replace(/%ID_SUFFIX%/g, localSfx);
					html = html.replace(/%FILE_NAME%/g, htmlEncode(file.name))
							.replace(/%FILE_SIZE%/g, size);
					$("#progressContainer" + mif.sfx).append(html);
					$("#progressCancel" + localSfx).click(function() {
						mif._canceledFiles[j] = true;
						$("#progressRow" + localSfx).remove();
						return false
					})
				}
			}
		};
		CB7.Form.MultiInputFile.DropAreaUI.prototype = {
			initVals : function() {
				this._dragging = false;
				this._countOver = 0;
				this._checkPoint = 0;
				this._isChecking = false
			},
			bodyDragover : function() {
				this._countOver++;
				if (this._dragging === false) {
					this._dragging = true;
					$("#dropArea" + this.sfx).show()
				}
				if (!this._isChecking) {
					this._isChecking = true;
					this._isLeft()
				}
			},
			dragAreaDrop : function() {
				this._dragging = false;
				$("#dropArea" + this.sfx).hide()
			},
			dragAreaDragover : function() {
				this._countOver++
			},
			_isLeft : function() {
				if (this._checkPoint < this._countOver) {
					this._checkPoint = this._countOver;
					var that = this;
					this._timeoutID = setTimeout(function() {
						that._isLeft()
					}, 300)
				} else {
					clearTimeout(this._timeoutID);
					$("#dropArea" + this.sfx).hide();
					this.initVals()
				}
			}
		}
	})()
};