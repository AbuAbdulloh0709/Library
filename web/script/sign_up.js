$('input[type=tel]').on('keydown', function () {
    $(this).data('val', $(this).val());
});

$('input[type=tel]').bind('input', function () {
    if ($(this).data('val').length < $(this).val().length) {
        if ($(this).val().length === 4)
            $(this).val($(this).val() + '(');
        if ($(this).val().length === 7)
            $(this).val($(this).val() + ')');
        if ($(this).val().length === 11)
            $(this).val($(this).val() + '-');
        if ($(this).val().length === 14)
            $(this).val($(this).val() + '-');
    }
    if ($(this).data('val').length > $(this).val().length) {
        let symbol = $(this).data('val').charAt($(this).val().length);
        if (symbol === '(' || symbol === ')' || symbol === '-') {
            $(this).val($(this).val().substr(0, $(this).val().length - 1));
        }
    }
});

$("input[type=tel]").bind("keydown click focus", function () {
    if ($(this).val().length === 0)
        $(this).val('+');
});