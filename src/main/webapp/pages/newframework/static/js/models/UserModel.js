define(["app"], function(app){
    var UserModel = Backbone.Model.extend({
        initialize: function(){
            _.bindAll(this);
        },
        defaults: {
            username: '',
            password: '',
            randCode: ''
        },
        url: function(){
            return app.API + '/user';
        }
    });
    return UserModel;
});

