

function sendToServer(form) {
		//var i = getSelectedDocument(form.documents)

		//alert(form.xmlsignature.value)
		//form.submit()
	
}



function signSelectedDocument(form) {
		alert("aaaaaaaa")
		var docGroup = form.documents;
		for (var  i = 0; i < docGroup.length; i++ ) {
			if(docGroup[i].checked) {
				alert("je zaskrtnuto" + docGroup[i].value + ".... "+ form.elements[i+"_fileToBeSigned"].value)
				
			}
		}
}


function singleSelectCheckbox(form,checkbox) {
	var docGroup = form.getsign;
	for(var i = 0; i < docGroup.length; i++) {
		if(docGroup[i].value == checkbox.value) {
			continue;
		} else {
			docGroup[i].checked = false;
		}
	}
	if(checkbox.checked == true) {
		var docURL = form.elements[checkbox.value+"_fileToBeSigned"].value;
		alert (docURL);
		//var serverURL = form.serverURL.value;
		var serverURL = "https://localhost:8443/dsigcollector";
		alert (serverURL);
		var greeting = dsigApplet.setURLs(docURL,serverURL);
		
	} 
	
}

		function signDocument(form){
		      var greeting = dsigApplet.setFileURLAndSignIt('http://sportzbraslav.org/budejovicepraha/img/vysledky_cbp_2010.pdf');
		      alert(greeting);
		}

 