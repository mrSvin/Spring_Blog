(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["errorModal"], {
    "69be": function (r, t, e) {
        "use strict";
        e.r(t);
        var s = function () {
            var r = this, t = r.$createElement, s = r._self._c || t;
            return s("transition", {attrs: {name: "modal"}}, [r.isErrors ? s("div", {staticClass: "ErrorModal"}, [s("div", {
                staticClass: "ErrorModal-Close",
                on: {click: r.onClose}
            }, [s("svg", {staticClass: "Icon Icon--close"}, [s("use", {attrs: {"xlink:href": e("5754") + "#delete"}})])]), s("div", {staticClass: "ErrorModal-Errors"}, r._l(r.errors, (function (t, e, o) {
                return s("div", {key: o, staticClass: "ErrorModal-Item"}, [r._v(" " + r._s(t) + " ")])
            })), 0)]) : r._e()])
        }, o = [], n = (e("b64b"), e("5530")), i = e("2f62"), a = {
            data: function () {
                return {closeTimer: null}
            }, computed: {
                errors: function () {
                    return this.$store.getters.errors
                }, isErrors: function () {
                    return Object.keys(this.errors).length
                }
            }, watch: {
                errors: function (r) {
                    var t = this;
                    r && (clearTimeout(this.closeTimer), this.closeTimer = setTimeout((function () {
                        return t.clearErrors()
                    }), 5e3))
                }
            }, methods: Object(n["a"])({}, Object(i["mapMutations"])(["clearErrors"]), {
                onClose: function () {
                    this.clearErrors()
                }
            })
        }, c = a, l = (e("d882"), e("2877")), u = Object(l["a"])(c, s, o, !1, null, null, null);
        t["default"] = u.exports
    }, "7be63": function (r, t, e) {
    }, d882: function (r, t, e) {
        "use strict";
        var s = e("7be63"), o = e.n(s);
        o.a
    }
}]);
//# sourceMappingURL=errorModal.af33cefc.js.map