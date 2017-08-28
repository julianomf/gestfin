var s, Toyota = Toyota || {};

Toyota.Menu = {
    sidr: {
        side: "right",
        name: "side-menu",
        timing: "ease-in-out",
        speed: 500, source: function () { $(document.getElementById(this.name)).show() }
    }, init: function (e) {
        s = e, Object.defineProperty(s, "$el", { get: function () { return $(s.el) } }), this.bind(e)
    }, bind: function (e) { s.$el.sidr(this.sidr) }
};