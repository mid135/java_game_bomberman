define([
    'jquery',
    'backbone'
], function ($, Backbone) {
    return function(method, model, options) {

        var methodMap = {
            'create': {
                method: 'POST',
                url: '/scoreboard/new',
                success: function (resp) {
                    if (resp.status == 1) {
                    }
                    else if (resp.status == 2) {
                    }
                },
                error: function () {

                }
            },
            'read': {
                method: 'GET',
                url: '/scoreboard',
                success: function (resp) {
                    if (resp.status == 1) {
                        model.reset(resp.scores);
                        try {
                            console.log(JSON.stringify(resp.scores));
                            localStorage['scores'] = JSON.stringify(resp).scores;
                        }
                        catch (e) {
                            console.error(e.message);
                        }
                    }
                },
                error: function () {
                console.log(resp.status);
                    if (window.localStorage && localStorage['scores']) {
                        model.reset(JSON.parse(localStorage['scores']));
                    }
                }
            },
            'update': {

            }
        };
        var type = methodMap[method].method,
            url = methodMap[method].url,
            success = methodMap[method].success,
            error = methodMap[method].error || function () {};

        var xhr = $.ajax({
            type: type,
            url: url,
            data: (model instanceof Backbone.Collection) ? model.toJSON() : {},
            dataType: 'json'
        }).done(success).fail(error);

        return xhr;
    }
});