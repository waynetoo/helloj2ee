var simpleValidation = {};
simpleValidation.getElmID = function(elm) {
	return elm.id ? elm.id : elm.name;
}
simpleValidation._validation = function(inputs){
	var pass = true;
	for(var i = 0; i < inputs.length; i++){
		var input = inputs[i];
		var className = input.className;
		var v = input.value;
		for(var j = 0; j < simpleValidation.validations.length; j++){
			var clazzName = simpleValidation.validations[j].className;
			if(className.indexOf(clazzName)<0)continue;
			var fun = simpleValidation.validations[j].checkFunction;
			var errorMsg = simpleValidation.validations[j].errorMsg;
			var adviceDiv = document.getElementById("advice-" + simpleValidation.getElmID(input));
			if(!fun(v)){
				pass = false;
				if(className.indexOf("validation-failed")<0)input.className+=" validation-failed";
				if(errorMsg != null && adviceDiv == null){
					advice = '<div class="validation-advice" id="advice-' + simpleValidation.getElmID(input) +'" style="display:block">' + errorMsg + '</div>'
					new Insertion.After(input, advice);
				}
			}else{
				if(adviceDiv != null)adviceDiv.style.display="none";
				if(className.indexOf("validation-failed")>-1)input.className=className.replace(" validation-failed","");
			}
		}
	}
	return pass;
	
}
simpleValidation.validation = function(form){
	var eles = [];
	var inputs = form.getElementsByTagName("input");
	var textateas = form.getElementsByTagName("textarea");
	for(var i = 0; i < inputs.length; i++)eles.push(inputs[i]);
	for(var i = 0; i < textateas.length; i++)eles.push(textateas[i]);
	return simpleValidation._validation(eles);
}

simpleValidation.validationIds = function(ids){
	var inputs = [];
	for(var i = 0; i < ids.length; i++){
		inputs[i] = document.getElementById(ids[i]);
	}
	return simpleValidation._validation(inputs);
}
simpleValidation.isEmpty=function(v){return v == null || v == "";}
simpleValidation.validations = [
                                {
                                	className : "required",
                                	checkFunction : function(v){
                                		return !simpleValidation.isEmpty(v);
                                	},
                                	errorMsg : "必填项目"
                                },
                                {
                                	className : "validate-digits",
                                	checkFunction : function(v){
                                		return simpleValidation.isEmpty(v) || !/[^\d]/.test(v);
                                	},
                                	errorMsg : "请填写数字"
                                },
                                {
                                	className : "validate-number",
                                	checkFunction : function(v){
                                		return simpleValidation.isEmpty(v) ||(!isNaN(v) && !/^\s+$/.test(v));
                                	},
                                	errorMsg : "请填写数字"
                                },
                                {
                                	className : 'validate-time-cn',
                                	checkFunction : function(v) {
                        				if(v == null || v == "") return true;
                        				var regex = /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;
                        				if(!regex.test(v)){return false};
                        				return true;
                        				//var d = new Date(v.replace(regex, '$1'),v.replace(regex, '$2'),v.replace(regex, '$3'));
                        				//return ( parseInt(RegExp.$2, 10) == (1+d.getMonth()) ) && 
                        							//(parseInt(RegExp.$3, 10) == d.getDate()) && 
                        							//(parseInt(RegExp.$1, 10) == d.getFullYear() );
                        			},
                                	errorMsg : "请填写日期和时间yyyy-MM-dd HH:mm:ss"
                                },
                                {
                                	className : 'validate-date-cn',
                                	checkFunction : function(v) {
                        				if(v == null || v == "") return true;
                        				var regex = /^(\d{4})-(\d{2})-(\d{2})$/;
                        				if(!regex.test(v)){return false};
                        				return true;
                        				//var d = new Date(v.replace(regex, '$1'),v.replace(regex, '$2'),v.replace(regex, '$3'));
                        				//return ( parseInt(RegExp.$2, 10) == (1+d.getMonth()) ) && 
                        							//(parseInt(RegExp.$3, 10) == d.getDate()) && 
                        							//(parseInt(RegExp.$1, 10) == d.getFullYear() );
                        			},
                                	errorMsg : "请填写日期yyyy-MM-dd"
                                }
                                
                                ];
try{ if(yn != null){} }catch(e){yn = {};}
yn.validation = simpleValidation.validation;