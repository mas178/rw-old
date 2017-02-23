$(function () {
    $('select').select2();

    if (document.getElementById('plannedTimestamp') != null && document.getElementById('actualTimestamp') != null) {
        console.log("Found plannedTimestamp and actualTimestamp!!");

        var $plannedTimestamp = $('#plannedTimestamp');
        var $actualTimestamp = $('#actualTimestamp');

        var planned = $plannedTimestamp.val();
        var actual = $actualTimestamp.val();

        console.log("planned1:", planned);
        console.log("actual1:", actual);

        if (planned == "" || planned == null) {
            planned = new Date();
        } else {
            planned = new Date(planned.replace(/\s\(.+\)/g, '').replace('時', ':00:00'));
        }

        if (actual == "" || actual == null) {
            actual = new Date();
        } else {
            actual = new Date(actual.replace(/\s\(.+\)/g, '').replace('時', ':00:00'));
        }

        console.log("planned2:", planned);

        $plannedTimestamp.datetimepicker({
            format: 'YYYY/MM/DD (dd) HH時',
            locale: 'ja',
            inline: true,
            sideBySide: true,
            defaultDate: planned
        });

        console.log("actual2:", actual);

        $actualTimestamp.datetimepicker({
            format: 'YYYY/MM/DD (dd) HH時',
            locale: 'ja',
            inline: true,
            sideBySide: true,
            defaultDate: actual
        });

        console.log("End");
    }

    if (document.getElementById('companyId')) {
        $('#companyId').change(function () {
            $('#formToGetJobs')
                .append($('<input/>', { type: 'hidden', name: 'select', value: true }))
                .append($('<input/>', { type: 'hidden', name: 'applicantId', value: $('#applicantId').val() }))
                .append($('<input/>', { type: 'hidden', name: 'companyId', value: $('#companyId').val() }))
                .append($('<input/>', { type: 'hidden', name: 'description', value: $('#description').val() }))
                .appendTo(document.body)
                .submit()
                .hide()
                .remove();
        });
    }

    if (document.getElementById('companyIds')) {
        $('#companyIds').change(function () {
            $('#formToGetJobs')
                .append($('<input/>', { type: 'hidden', name: 'select', value: true }))
                .append($('<input/>', { type: 'hidden', name: 'username', value: $('#username').val() }))
                .append($('<input/>', { type: 'hidden', name: 'plannedTimestamp', value: $('#plannedTimestamp').val() }))
                .append($('<input/>', { type: 'hidden', name: 'actualTimestamp', value: $('#actualTimestamp').val() }))
                .append($('<input/>', { type: 'hidden', name: 'plannedAction', value: $('#plannedAction').val() }))
                .append($('<input/>', { type: 'hidden', name: 'actualAction', value: $('#actualAction').val() }))
                .append($('<input/>', { type: 'hidden', name: 'name', value: $('#name').val() }))
                .append($('<input/>', { type: 'hidden', name: 'description', value: $('#description').val() }))
                .append($('<input/>', { type: 'hidden', name: 'applicantIds', value: $('#applicantIds').val() }))
                .append($('<input/>', { type: 'hidden', name: 'companyIds', value: $('#companyIds').val() }))
                .append($('<input/>', { type: 'hidden', name: 'jobIds', value: $('#jobIds').val() }))
                .append($('<input/>', { type: 'hidden', name: 'applicationIds', value: $('#applicationIds').val() }))
                .appendTo(document.body)
                .submit()
                .hide()
                .remove();
        });
    }
});
