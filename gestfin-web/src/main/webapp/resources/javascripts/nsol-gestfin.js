//---------------------------------------------------------------------------//
//                     Rotinas ligadas às janelas popup                      //
//---------------------------------------------------------------------------//
/**
* Vertically center Bootstrap 3 modals so they aren't always stuck at the top
*/
$(document).ready(function () {
    function reposition() {
        var modal = $(this),
            dialog = modal.find('.modal-dialog');
        modal.css('display', 'block');

        // Dividing by two centers the modal exactly, but dividing by three 
        // or four works better for larger screens.
        dialog.css("margin-top", Math.max(0, ($(window).height() - dialog.height()) / 2));
    }
    // Reposition when a modal is shown
    $('.modal').on('show.bs.modal', reposition);
    // Reposition when the window is resized
    $(window).on('resize', function () {
        $('.modal:visible').each(reposition);
    });

    $('.modal').on('show.bs.modal', function () {
        if ($(document).height() > $(window).height()) {
            // no-scroll
            $('body').addClass("modal-open-noscroll");
        }
        else {
            $('body').removeClass("modal-open-noscroll");
        }
    })
    $('.modal').on('hide.bs.modal', function () {
        $('body').removeClass("modal-open-noscroll");
    })
});

//---------------------------------------------------------------------------//
//         Rotinas para aplicar formatação durante o input dos dados         //
//---------------------------------------------------------------------------//

//-- Formatação de placa
var plateBehavior = function (val) {
	return 'SSS-0A00';
}, plateOptions = {
    onKeyPress: function (val, e, field, options) {
        field.mask(plateBehavior.apply({}, arguments), options);
    }
};
function applyPlateMask(selector){
	$(selector).mask(plateBehavior, plateOptions);
}

//-- Formatação de telefone
var phoneMaskBehavior = function (val) {
    return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
}, phoneMaskOptions = {
    onKeyPress: function (val, e, field, options) {
        field.mask(phoneMaskBehavior.apply({}, arguments), options);
    }
};
function applyPhoneMask(selector){
	$(selector).mask(phoneMaskBehavior, phoneMaskOptions);
}

//-- Formatação de CPF/CNPJ
var cpfCnpjBehavior = function (val) {
    return (val.length > 14) ? '00.000.000/0000-00' : '000.000.000-000';
}, cpfCnpjOptions = {
    onKeyPress: function (val, e, field, options) {
        field.mask(cpfCnpjBehavior.apply({}, arguments), options);
    }
};
function applyCpfCnpjMask(selector){
	$(selector).mask(cpfCnpjBehavior, cpfCnpjOptions);
}

//-- Formatação de CPF
var cpfBehavior = function (val) {
    return '000.000.000-000';
}, cpfOptions = {
    onKeyPress: function (val, e, field, options) {
        field.mask(cpfBehavior.apply({}, arguments), options);
    }
};
function applyCpfMask(selector){
	$(selector).mask(cpfBehavior, cpfOptions);
}

//-- Formatação de CNPJ
var cnpjBehavior = function (val) {
    return '00.000.000/0000-00';
}, cnpjOptions = {
    onKeyPress: function (val, e, field, options) {
        field.mask(cnpjBehavior.apply({}, arguments), options);
    }
};
function applyCnpjMask(selector){
	$(selector).mask(cnpjBehavior, cnpjOptions);
}

//-- Formatação de CNAE
var cnaeBehavior = function (val) {
    return '0000-0/00';
}, cnaeOptions = {
    onKeyPress: function (val, e, field, options) {
        field.mask(cnaeBehavior.apply({}, arguments), options);
    }
};
function applyCnaeMask(selector){
	$(selector).mask(cnaeBehavior, cnaeOptions);
}

//-- Formatação de CNAE
var postalCodeBehavior = function (val) {
    return '00000-000';
}, postalCodeOptions = {
    onKeyPress: function (val, e, field, options) {
        field.mask(postalCodeBehavior.apply({}, arguments), options);
    }
};
function applyPostalCodeMask(selector){
	$(selector).mask(postalCodeBehavior, postalCodeOptions);
}

//-- Formatação de campo monetário
function applyCurrencyMask(selector, mask) {
	$(selector).mask(mask, {reverse: true});
}

//-- Formatação de campo percentual
function applyPercentMask(selector, mask) {
	$(selector).mask(mask, {reverse: true});
}

//-- Formatação de campo kilometragem
function applyMileageMask(selector) {
    $(selector).mask('###.###.##0', { reverse: true });
}

//-- Formatação de campo data
function applyDateMask(selector, mask, format, language) {
    $(selector).mask(mask);
    $(selector).datepicker({
        format: format,
        todayBtn: "linked",
        language: language,
        autoclose: true
    });
}

//-- Formatação de campo espessura do pneu
function applyTireDepthMask(selector) {
    $(selector).mask('00', { reverse: true });
}

//-- Formatação de campo data de expiração
function applyExpirationDateMask(selector) {
    $(selector).mask('###', { reverse: true });
}

function applyAlphaMask(selector) {
	var maxlength = $(selector).attr("maxlength");
	//alert(maxlength);
	if (maxlength != null) {
		var mask = '';
		for (i = 0; i < maxlength; i++) {
			mask += 'A';
		}	
	    $(selector).mask(mask);
	}
}

//-- Tratamento para forçar a validação do maxlength pois em dispositivos Android não respeita o atributo
function applyAndroidMaxLength(selector) {
    max_length = $(selector).attr("maxlength");
    $(selector).on("keyup", function() {
        if(this.value.length > max_length) {
            $(this).val($(this).val().substr(0, max_length));
        }
    });
}

function applyNumericMask(selector) {
	var maxlength = $(selector).attr("maxlength");
	if (maxlength != null) {
		var mask = '';
		for (i = 0; i < maxlength; i++) {
			mask += '0';
		}	
	    $(selector).mask(mask);
	}
}

//-- Formatação de CNPJ apenas
var cnpjBehavior = function (val) {
    return '00.000.000/0000-00';
}, cnpjOptions = {
    onKeyPress: function (val, e, field, options) {
        field.mask(cnpjBehavior.apply({}, arguments), options);
    }
};
function applyCnpjMask(selector){
	$(selector).mask(cnpjBehavior, cnpjOptions);
}

//---------------------------------------------------------------------------//
//-- Habilita/desabilita componentes
//---------------------------------------------------------------------------//
function disableControl(selector) {
	$(selector).attr('disabled','disabled');	
}
function enableControl(selector) {
	$(selector).removeAttr('disabled');	
}

function forceLoadingPanelClose() {
	$('#ajax_loader').modal('hide');
	$('body').removeClass('modal-open');
	$('.modal-backdrop').remove();						        	
}