(function ($) {
    "use strict";


    /*==================================================================
    [ Validate after type ]*/
    $('.validate-input .input100').each(function () {
        $(this).on('blur', function () {
            if (validate(this) == false) {
                showValidate(this);
            } else {
                $(this).parent().addClass('true-validate');
            }
        })
    })


    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit', function () {
        var check = true;

        for (var i = 0; i < input.length; i++) {
            if (validate(input[i]) == false) {
                showValidate(input[i]);
                check = false;
            }
        }

        return check;
    });


    $('.validate-form .input100').each(function () {
        $(this).focus(function () {
            hideValidate(this);
            $(this).parent().removeClass('true-validate');
        });
    });
    Date.prototype.toDateInputValue = (function () {
        var local = new Date(this);
        local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
        return local.toJSON().slice(0, 10);
    });
    $(document).ready(function () {
        console.log("ready!");
        document.getElementById('date').value = new Date().toDateInputValue();
    });

    imgInp.onchange = evt => {
        const [file] = imgInp.files
        if (file) {
            blah.style.visibility = 'visible';
            blah.src = URL.createObjectURL(file)
            var removeButton = document.getElementById("removeImageButton");
            removeButton.style.visibility = 'visible';
        } else {
            setImageVisible("blah", "hidden")
        }
    }

    imgInp2.onmousedown = evt => {
        const [file] = imgInp2.files
        if (file) {
            blah.style.visibility = 'visible';
            blah2.src = URL.createObjectURL(file)
            var removeButton = document.getElementById("removeImageButton2");
            removeButton.style.visibility = 'visible';
        } else {
            setImageVisible("blah2", "hidden")
        }
    }

    removeImageButton.onmousedown = () => {
        var removeButton = document.getElementById("removeImageButton");
        blah.src = "#";
        blah.style.visibility = 'hidden';
        removeButton.style.visibility = 'hidden';
        imgInp.files.splice(1);
    }

    removeImageButton2.onmousedown = () => {
        var removeButton = document.getElementById("removeImageButton2");
        blah2.src = "#";
        blah2.style.visibility = 'hidden';
        removeButton.style.visibility = 'hidden';
    }

    function validate(input) {
        if ($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if ($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        } else {
            if ($(input).val().trim() == '') {
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');

        $(thisAlert).append('<span class="btn-hide-validate">&#xf136;</span>')
        $('.btn-hide-validate').each(function () {
            $(this).on('click', function () {
                hideValidate(this);
            });
        });
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();
        $(thisAlert).removeClass('alert-validate');
        $(thisAlert).find('.btn-hide-validate').remove();
    }


})(jQuery);