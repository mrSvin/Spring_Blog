(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["login"], {
    "013f": function (t, e, n) {
        "use strict";
        n.r(e);
        var r = function () {
            var t = this, e = t.$createElement, n = t._self._c || e;
            return n("main", {class: ["restorePassword" === t.path ? "Login Login--wide Wrapper" : "Login Wrapper"]}, [n("div", {staticClass: "Login-Section"}, [n("LoginHeader", [t._v(" " + t._s(t.title) + " ")]), n("router-view")], 1)])
        }, i = [], s = (n("b0c0"), n("d3b7"), n("5530")), o = n("2f62"), a = function () {
            return n.e("loginHeader").then(n.bind(null, "7fcf"))
        }, u = {
            name: "Login",
            props: {className: {type: String, required: !1}},
            computed: Object(s["a"])({}, Object(o["mapGetters"])(["isAuth"]), {
                path: function () {
                    return this.$route.name
                }, title: function () {
                    switch (this.$route.path) {
                        case"/login":
                            return "Вход";
                        case"/login/registration":
                        case"/login/registration-success":
                            return "Регистрация";
                        case"/login/restore-password":
                            return "Восстановление пароля";
                        default:
                            return "Смена пароля"
                    }
                }
            }),
            watch: {
                isAuth: function () {
                    this.isAuth && this.$router.push("/")
                }
            },
            methods: Object(s["a"])({}, Object(o["mapMutations"])(["clearAuthErrors"])),
            components: {LoginHeader: a},
            mounted: function () {
                this.clearAuthErrors()
            }
        }, c = u, l = (n("50ba"), n("2877")), p = Object(l["a"])(c, r, i, !1, null, null, null);
        e["default"] = p.exports
    }, "50ba": function (t, e, n) {
        "use strict";
        var r = n("eb90"), i = n.n(r);
        i.a
    }, eb90: function (t, e, n) {
    }
}]);
//# sourceMappingURL=login.61bd44e3.js.map