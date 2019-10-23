(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/*!**********************************************************!*\
  !*** ./src/$$_lazy_route_resource lazy namespace object ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error('Cannot find module "' + req + '".');
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/activity/activity.component.html":
/*!**************************************************!*\
  !*** ./src/app/activity/activity.component.html ***!
  \**************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"stock-pan\">\n  <div class=\"row\">\n    <div class=\"col-md-10 \">\n      <h2> FII's data.</h2>\n    </div>\n    <!-- <div class=\"col-md-2\">\n      <button class=\"btn btn-default\" (click)=\"loadData()\"> Import Data</button>\n    </div> -->\n  </div>\n  <div class=\"row seperator\">\n    <div class=\"col-md-12\">\n      <div class=\"row seperator\">\n      </div>\n    </div>\n  </div>\n  <div class=\"row seperator\">\n    <div class=\"col-md-2\">\n      <label for=\"start\">Start date:</label>\n      <input class=\"form-control\" type=\"date\" id=\"start\" name=\"startDate\"\n        (change)=\"onDateChange($event.target.value,true)\" />\n    </div>\n    <div class=\"col-md-2\">\n      <label for=\"start\">End date:</label>\n      <input class=\"form-control\" type=\"date\" id=\"end\" name=\"endDate\"\n        (change)=\"onDateChange($event.target.value,false)\">\n    </div>\n  </div>\n  <div class=\"row\">\n    <div class=\"col-md-12\">\n      <div class=\"row\">\n        <div class=\"col-md-12\">\n          <div *ngFor=\"let chk of checkBoxFilter\" class=\"checkbox check-container\"\n            style=\"margin-left:10px; margin-right:10px;\">\n            <label><input type=\"checkbox\" (change)=\"checkValue($event.target.checked,chk,'CALL')\">{{chk.name}}</label>\n            <!-- <label class=\"switch\" style=\"margin-left:10px;margin-top: 9px;\">\n              <input type=\"checkbox\" checked (change)=\"directionValue($event.target.checked,chk)\">\n              <span class=\"slider round\"></span>\n            </label> -->\n          </div>\n        </div>\n        <div class=\"col-md-12\">\n          <div class=\"row\">\n            <div class=\"col-md-10\">\n            </div>\n          </div>\n          <table id=\"customers\">\n            <tr>\n              <th>Date</th>\n              <th>Future Index Long</th>\n              <th>Future Index Short</th>\n              <th>Option Index Put Long</th>\n              <th>Option Index Put Short</th>\n              <th>Option Index Call Long</th>\n              <th>Option Index Call Short</th>\n              <th>Option Stock Call Long</th>\n              <th>Option Stock Call Short</th>\n            </tr>\n            <tr *ngFor=\"let activity of activities;let i = index\">\n              <td>{{activity.date| date: 'dd/MM/yyyy'}}</td>\n              <td>{{activity.futureIdxLong}}</td>\n              <td>{{activity.futureIdxShort}}</td>\n              <td>{{activity.optionIdxPutLong}}</td>\n              <td>{{activity.optionIdxPutShort}}</td>\n              <td>{{activity.optionIdxCallLong}}</td>\n              <td>{{activity.optionIdxCallShort}}</td>\n              <td>{{activity.optionStockCallLong}}</td>\n              <td>{{activity.optionStockCallShort}}</td>\n            </tr>\n          </table>\n        </div>\n      </div>\n    </div>\n    </div>\n</div>"

/***/ }),

/***/ "./src/app/activity/activity.component.ts":
/*!************************************************!*\
  !*** ./src/app/activity/activity.component.ts ***!
  \************************************************/
/*! exports provided: ActivityComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ActivityComponent", function() { return ActivityComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _service_stock_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../service/stock.service */ "./src/app/service/stock.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ActivityComponent = /** @class */ (function () {
    function ActivityComponent(router, stockservice) {
        this.router = router;
        this.stockservice = stockservice;
        this.activities = [];
    }
    ActivityComponent.prototype.ngOnInit = function () {
    };
    ActivityComponent.prototype.loadData = function () {
        console.log("Loading data into DB");
        this.stockservice.loadActivityData().subscribe(function (data) {
        });
    };
    ;
    ActivityComponent.prototype.getData = function () {
        var _this = this;
        this.stockservice.getActivity(this.startDate, this.endDate)
            .subscribe(function (data) {
            _this.activities = data;
        });
    };
    ActivityComponent.prototype.onDateChange = function (value, isStartDate) {
        if (isStartDate) {
            this.startDate = value;
        }
        else
            this.endDate = value;
        this.getData();
    };
    ActivityComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-activity',
            template: __webpack_require__(/*! ./activity.component.html */ "./src/app/activity/activity.component.html")
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"], _service_stock_service__WEBPACK_IMPORTED_MODULE_1__["StockService"]])
    ], ActivityComponent);
    return ActivityComponent;
}());



/***/ }),

/***/ "./src/app/all-load-data/all-load-data.component.css":
/*!***********************************************************!*\
  !*** ./src/app/all-load-data/all-load-data.component.css ***!
  \***********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\r\n.seperator {\r\n    margin-top: 10px;\r\n    }\r\n    label {\r\n        font: bold;\r\n    }\r\n    .sort-arrow{\r\n        cursor: pointer;\r\n    }\r\n    .switch {\r\n        position: relative;\r\n        display: inline-block;\r\n        width: 35px;\r\n        height: 15px;\r\n      }\r\n    .switch input { \r\n        opacity: 0;\r\n        width: 0;\r\n        height: 0;\r\n      }\r\n    .slider {\r\n        position: absolute;\r\n        cursor: pointer;\r\n        top: 0;\r\n        left: 0;\r\n        right: 0;\r\n        bottom: 0;\r\n        background-color: #ccc;\r\n        transition: .4s;\r\n      }\r\n    .slider:before {\r\n        position: absolute;\r\n        content: \"\";\r\n        height: 15px;\r\n        width: 15px;\r\n        left: -6px;\r\n        bottom: 0px;\r\n        background-color: white;\r\n        transition: .4s;\r\n      }\r\n    input:checked + .slider {\r\n        background-color: #2196F3;\r\n      }\r\n    input:focus + .slider {\r\n        box-shadow: 0 0 1px #2196F3;\r\n      }\r\n    input:checked + .slider:before {\r\n        -webkit-transform: translateX(26px);\r\n        transform: translateX(26px);\r\n      }\r\n    /* Rounded sliders */\r\n    .slider.round {\r\n        border-radius: 34px;\r\n      }\r\n    .slider.round:before {\r\n        border-radius: 50%;\r\n      }"

/***/ }),

/***/ "./src/app/all-load-data/all-load-data.component.html":
/*!************************************************************!*\
  !*** ./src/app/all-load-data/all-load-data.component.html ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"stock-pan\">\n  <div class=\"row\">\n      <div class=\"col-md-6 \">\n          <h2> Welcome to Money World......!</h2>\n      </div>\n  </div>\n  <div class=\"row seperator\"></div>\n<div class=\"row\">\n  <div class=\"col-md-6 \">\n      <h4> All data load details Details </h4>\n    </div>\n </div>\n <div class=\"row seperator\"></div>\n  <div class=\"row\">\n    <div class=\"col-md-12\">\n      <div class=\"panel panel-default\">\n        <div class=\"panel-body\">\n          <div class=\"row\">\n            <div class=\"col-md-4 \">\n            <label> Option Chain Nifty.</label>\n            </div>\n            <div class=\"col-md-2\">\n              <button class=\"btn btn-default\" (click)=\"loadNiftyOptionsData()\"> Import Data</button>\n            </div>\n          </div>\n          <div class=\"row seperator\"></div>\n          <div class=\"row\">\n            <div class=\"col-md-4 \">\n              <label> Stocks Options Data.</label>\n            </div>\n            <div class=\"col-md-2\">\n              <button class=\"btn btn-default\" (click)=\"loadStocOptionskData()\"> Import Data</button>\n            </div>\n          </div>\n          <div class=\"row seperator\"></div>\n          <div class=\"row\">\n            <div class=\"col-md-4 \">\n              <label> Stocks List.</label>\n            </div>\n            <div class=\"col-md-2\">\n              <button class=\"btn btn-default\" (click)=\"loadStockData()\"> Import Data</button>\n            </div>\n          </div>\n          <div class=\"row seperator\"></div>\n          <div class=\"row\">\n            <div class=\"col-md-4 \">\n              <label> FII's data.</label>\n            </div>\n            <div class=\"col-md-2\">\n              <button class=\"btn btn-default\" (click)=\"loadFiiData()\"> Import Data</button>\n            </div>\n          </div>\n        </div>\n      </div>\n    </div>\n  </div>\n  </div>"

/***/ }),

/***/ "./src/app/all-load-data/all-load-data.component.ts":
/*!**********************************************************!*\
  !*** ./src/app/all-load-data/all-load-data.component.ts ***!
  \**********************************************************/
/*! exports provided: AllLoadDataComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AllLoadDataComponent", function() { return AllLoadDataComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _service_stock_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../service/stock.service */ "./src/app/service/stock.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AllLoadDataComponent = /** @class */ (function () {
    function AllLoadDataComponent(router, stockservice) {
        this.router = router;
        this.stockservice = stockservice;
        this.stocks = [];
    }
    AllLoadDataComponent.prototype.ngOnInit = function () {
    };
    AllLoadDataComponent.prototype.loadStockData = function () {
        var _this = this;
        console.log("Loading Stock data into DB");
        this.stockservice.loadData().subscribe(function (data) {
            _this.stocks = data;
        });
    };
    ;
    AllLoadDataComponent.prototype.loadNiftyOptionsData = function () {
        console.log("Loading Nifty Options data into DB");
        this.stockservice.loadEquity().subscribe(function (data) {
        });
    };
    ;
    AllLoadDataComponent.prototype.loadStocOptionskData = function () {
        console.log("Loading Nifty Options data into DB");
        this.stockservice.loadStockOptions().subscribe(function (data) {
        });
    };
    AllLoadDataComponent.prototype.loadFiiData = function () {
        console.log("Loading data into DB");
        this.stockservice.loadActivityData().subscribe(function (data) {
        });
    };
    ;
    AllLoadDataComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-all-load-data',
            template: __webpack_require__(/*! ./all-load-data.component.html */ "./src/app/all-load-data/all-load-data.component.html"),
            styles: [__webpack_require__(/*! ./all-load-data.component.css */ "./src/app/all-load-data/all-load-data.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"], _service_stock_service__WEBPACK_IMPORTED_MODULE_1__["StockService"]])
    ], AllLoadDataComponent);
    return AllLoadDataComponent;
}());



/***/ }),

/***/ "./src/app/app.component.html":
/*!************************************!*\
  !*** ./src/app/app.component.html ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<app-menu></app-menu>\n<router-outlet></router-outlet>\n"

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppComponent = /** @class */ (function () {
    function AppComponent() {
        this.title = 'app';
    }
    AppComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-root',
            template: __webpack_require__(/*! ./app.component.html */ "./src/app/app.component.html")
        })
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _app_routing__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./app.routing */ "./src/app/app.routing.ts");
/* harmony import */ var _service_auth_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./service/auth.service */ "./src/app/service/auth.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _service_stock_service__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./service/stock.service */ "./src/app/service/stock.service.ts");
/* harmony import */ var _list_stocks_list_stocks_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./list-stocks/list-stocks.component */ "./src/app/list-stocks/list-stocks.component.ts");
/* harmony import */ var _menu_menu_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./menu/menu.component */ "./src/app/menu/menu.component.ts");
/* harmony import */ var _equity_equity_component__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ./equity/equity.component */ "./src/app/equity/equity.component.ts");
/* harmony import */ var _premiumdk_premiumdk_component__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./premiumdk/premiumdk.component */ "./src/app/premiumdk/premiumdk.component.ts");
/* harmony import */ var _activity_activity_component__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./activity/activity.component */ "./src/app/activity/activity.component.ts");
/* harmony import */ var _all_load_data_all_load_data_component__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! ./all-load-data/all-load-data.component */ "./src/app/all-load-data/all-load-data.component.ts");
/* harmony import */ var _stock_option_chain_stock_option_chain_component__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! ./stock-option-chain/stock-option-chain.component */ "./src/app/stock-option-chain/stock-option-chain.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};















var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            declarations: [
                _app_component__WEBPACK_IMPORTED_MODULE_2__["AppComponent"],
                _list_stocks_list_stocks_component__WEBPACK_IMPORTED_MODULE_8__["ListStocksComponent"],
                _menu_menu_component__WEBPACK_IMPORTED_MODULE_9__["MenuComponent"],
                _equity_equity_component__WEBPACK_IMPORTED_MODULE_10__["EquityComponent"],
                _premiumdk_premiumdk_component__WEBPACK_IMPORTED_MODULE_11__["PremiumdkComponent"],
                _activity_activity_component__WEBPACK_IMPORTED_MODULE_12__["ActivityComponent"],
                _all_load_data_all_load_data_component__WEBPACK_IMPORTED_MODULE_13__["AllLoadDataComponent"],
                _stock_option_chain_stock_option_chain_component__WEBPACK_IMPORTED_MODULE_14__["StockOptionChainComponent"]
            ],
            imports: [
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"],
                _app_routing__WEBPACK_IMPORTED_MODULE_3__["routing"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_5__["ReactiveFormsModule"],
                _angular_common_http__WEBPACK_IMPORTED_MODULE_6__["HttpClientModule"]
            ],
            providers: [_service_auth_service__WEBPACK_IMPORTED_MODULE_4__["AuthenticationService"], _service_stock_service__WEBPACK_IMPORTED_MODULE_7__["StockService"]],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_2__["AppComponent"]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/app.routing.ts":
/*!********************************!*\
  !*** ./src/app/app.routing.ts ***!
  \********************************/
/*! exports provided: routing */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "routing", function() { return routing; });
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _list_stocks_list_stocks_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./list-stocks/list-stocks.component */ "./src/app/list-stocks/list-stocks.component.ts");
/* harmony import */ var _equity_equity_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./equity/equity.component */ "./src/app/equity/equity.component.ts");
/* harmony import */ var _premiumdk_premiumdk_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./premiumdk/premiumdk.component */ "./src/app/premiumdk/premiumdk.component.ts");
/* harmony import */ var _activity_activity_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./activity/activity.component */ "./src/app/activity/activity.component.ts");
/* harmony import */ var _all_load_data_all_load_data_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./all-load-data/all-load-data.component */ "./src/app/all-load-data/all-load-data.component.ts");
/* harmony import */ var _stock_option_chain_stock_option_chain_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./stock-option-chain/stock-option-chain.component */ "./src/app/stock-option-chain/stock-option-chain.component.ts");







var routes = [
    { path: '', component: _all_load_data_all_load_data_component__WEBPACK_IMPORTED_MODULE_5__["AllLoadDataComponent"] },
    { path: 'stock', component: _list_stocks_list_stocks_component__WEBPACK_IMPORTED_MODULE_1__["ListStocksComponent"] },
    { path: 'stock-opitons-chain', component: _stock_option_chain_stock_option_chain_component__WEBPACK_IMPORTED_MODULE_6__["StockOptionChainComponent"] },
    { path: 'equity', component: _equity_equity_component__WEBPACK_IMPORTED_MODULE_2__["EquityComponent"] },
    { path: 'premium-dk', component: _premiumdk_premiumdk_component__WEBPACK_IMPORTED_MODULE_3__["PremiumdkComponent"] },
    { path: 'exchange-activity', component: _activity_activity_component__WEBPACK_IMPORTED_MODULE_4__["ActivityComponent"] }
    /* { path: 'all-data-download', component: AllLoadDataComponent } */
];
var routing = _angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"].forRoot(routes);


/***/ }),

/***/ "./src/app/equity/equity.component.css":
/*!*********************************************!*\
  !*** ./src/app/equity/equity.component.css ***!
  \*********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".grid-container {\r\n    display: -ms-grid;\r\n    display: grid;\r\n    -ms-grid-columns: auto auto auto auto;\r\n        grid-template-columns: auto auto auto auto;\r\n    grid-gap: 10px;\r\n    padding: 10px;\r\n  }\r\n.check-container {\r\n  width: 130px;\r\n    float: left;\r\n}\r\n.grid-col-container {\r\n    display: -ms-grid;\r\n    display: grid;\r\n    -ms-grid-columns: auto auto;\r\n        grid-template-columns: auto auto;\r\n    grid-gap: 10px;\r\n    padding: 10px;\r\n  }\r\n.grid-col-container > div {\r\n    background-color: rgba(255, 255, 255, 0.8);\r\n    text-align: center;\r\n  }\r\n.column-seprator {\r\n    /* Old Chrome, Safari and Opera */\r\n    -webkit-column-count: 3; \r\n    -webkit-column-gap: 40px;\r\n    \r\n    /* Old Firefox */\r\n    -moz-column-count: 3;\r\n    -moz-column-gap: 40px;\r\n  \r\n    /* Standard syntax */\r\n    column-count: 3;\r\n    column-gap: 40px;\r\n  }\r\n\r\n  "

/***/ }),

/***/ "./src/app/equity/equity.component.html":
/*!**********************************************!*\
  !*** ./src/app/equity/equity.component.html ***!
  \**********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"stock-pan\">\n  <div class=\"row\">\n    <div class=\"col-md-10 \">\n      <h2> Option Chain Nifty.</h2>\n    </div>\n   <!--  <div class=\"col-md-2\">\n      <button class=\"btn btn-default\" (click)=\"loadEquityData()\"> Import Data</button>\n    </div> -->\n  </div>\n  <div class=\"row seperator\">\n    <div class=\"col-md-12\">\n      <div class=\"row seperator\">\n      </div>\n    </div>\n  </div>\n  <div class=\"row seperator\">\n    <div class=\"col-md-2\">\n      <label for=\"start\">Strike Price:</label>\n      <input class=\"form-control\" type=\"text\" id=\"strikePrice\" name=\"strikePrice\"\n        (change)=\"onStrikeChange($event.target.value)\">\n    </div>\n    <div class=\"col-md-3\">\n      <label for=\"days\">Symbol:</label>\n      <select class=\"form-control\" (change)=\"symbolDropDown($event.target.value)\">\n          <option value=''>\n              Nifty\n            </option>\n        <option *ngFor=\"let symbol of symbols\" value={{symbol.symbol}}>\n          {{symbol.companyName}}\n        </option>\n      </select>\n    </div>\n    <div class=\"col-md-2\">\n      <label for=\"start\">Start date:</label>\n      <input class=\"form-control\" type=\"date\" id=\"start\" name=\"startDate\"\n        (change)=\"onDateChange($event.target.value,true)\" />\n    </div>\n    <div class=\"col-md-2\">\n      <label for=\"start\">End date:</label>\n      <input class=\"form-control\" type=\"date\" id=\"end\" name=\"endDate\"\n        (change)=\"onDateChange($event.target.value,false)\">\n    </div>\n  </div>\n  <div class=\"row\">\n    <div class=\"col-md-6\">\n      <div class=\"row\">\n        <div class=\"col-md-12\">\n          <div *ngFor=\"let chk of checkBoxFilter\" class=\"checkbox check-container\"\n            style=\"margin-left:10px; margin-right:10px;\">\n            <label><input type=\"checkbox\" (change)=\"checkValue($event.target.checked,chk,'CALL')\">{{chk.name}}</label>\n            <!-- <label class=\"switch\" style=\"margin-left:10px;margin-top: 9px;\">\n              <input type=\"checkbox\" checked (change)=\"directionValue($event.target.checked,chk)\">\n              <span class=\"slider round\"></span>\n            </label> -->\n          </div>\n        </div>\n        <div class=\"col-md-12\">\n          <div class=\"row\">\n            <div class=\"col-md-10\">\n              <h4>Call</h4>\n            </div>\n            <div class=\"col-md-1\">\n              <button class=\"btn btn-info\" (click)=\"getData('CALL')\"> Get Call</button>\n            </div>\n          </div>\n          <div class=\"row seperator\"></div>\n          <table id=\"customers\">\n            <tr>\n              <th >Date</th>\n              <th class=\"sort-arrow\" (click)=\"callSortBy('oi',!sortDir)\">OI</th>\n              <th class=\"sort-arrow\" (click)=\"callSortBy('chnginOI',!sortDir)\">COI</th>\n              <th>IV</th>\n              <th>LTP</th>\n              <th>Net-C</th>\n              <th>Vol</th>\n              <th class=\"sort-arrow\" (click)=\"callSortBy('strikePrice',!sortDir)\">SP</th>\n              <th class=\"sort-arrow\" (click)=\"callSortBy('postionsVol',!sortDir)\">V/CHI</th>\n            </tr>\n            {{callEquities.date}}\n            <tr *ngFor=\"let equity of callEquities;let i = index\">\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.date| date: 'dd/MM/yy'}}</td>\n              <td  *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.oi}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.chnginOI}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.iv}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.ltp}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.netChng}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.volume}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.strikePrice}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.postionsVol | number:'1.1-1'}}</td>\n            </tr>\n          </table>\n        </div>\n      </div>\n    </div>\n    <div class=\"col-md-6\">\n      <div class=\"row\">\n        <div class=\"col-md-12\">\n          <div *ngFor=\"let chk of checkBoxFilter\" class=\"checkbox check-container\"\n            style=\"margin-left:10px; margin-right:10px;\">\n            <label><input type=\"checkbox\" (change)=\"checkValue($event.target.checked,chk,'PUT')\">{{chk.name}}</label>\n            <!-- <label class=\"switch\" style=\"margin-left:10px;margin-top: 9px;\">\n              <input type=\"checkbox\" checked (change)=\"directionValue($event.target.checked,chk)\">\n              <span class=\"slider round\"></span>\n            </label> -->\n          </div>\n        </div>\n        <div class=\"col-md-12\">\n          <div class=\"row\">\n            <div class=\"col-md-10\">\n              <h4>PUT</h4>\n            </div>\n            <div class=\"col-md-1\">\n              <button class=\"btn btn-info\" (click)=\"getData('PUT')\"> Get Put</button>\n            </div>\n          </div>\n          <div class=\"row seperator\"></div>\n          <table id=\"customers\">\n            <tr>\n              <th class=\"sort-arrow\" (click)=\"putSortBy('postionsVol',!sortDir)\">V/CHI</th>\n              <th>Date</th>\n              <th  class=\"sort-arrow\" (click)=\"putSortBy('oi',!sortDir)\">OI</th>\n              <th class=\"sort-arrow\" (click)=\"putSortBy('chnginOI',!sortDir)\">C-OI</th>\n              <th>IV</th>\n              <th>LTP</th>\n              <th>Net-C</th>\n              <th>Vol</th>\n              <th class=\"sort-arrow\" (click)=\"putSortBy('strikePrice',!sortDir)\">SP</th>\n            </tr>\n            <tr *ngFor=\"let equity of putEquities;let i = index\">\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.postionsVol | number:'1.1-1'}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.date | date: 'dd/MM/yy'}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.oi}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.chnginOI}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.iv}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.ltp}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.netChng}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.volume}}</td>\n              <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.strikePrice}}</td>\n            </tr>\n          </table>\n        </div>\n      </div>\n    </div>\n\n\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/equity/equity.component.ts":
/*!********************************************!*\
  !*** ./src/app/equity/equity.component.ts ***!
  \********************************************/
/*! exports provided: EquityComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "EquityComponent", function() { return EquityComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _service_stock_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../service/stock.service */ "./src/app/service/stock.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var EquityComponent = /** @class */ (function () {
    function EquityComponent(stockservice) {
        this.stockservice = stockservice;
        this.equities = [];
        this.symbols = [];
        this.filtersRequest = [];
        this.callEquities = [];
        this.putEquities = [];
        this.search = { strikePrice: null, startDate: null, endDate: null, type: 'CALL' };
    }
    EquityComponent.prototype.ngOnInit = function () {
        this.getCheckfilter();
        // this.getSymbols();
        this.getEquities();
    };
    EquityComponent.prototype.getCheckfilter = function () {
        var _this = this;
        this.stockservice.getFilters("equityFilter").subscribe(function (data) {
            _this.checkBoxFilter = data;
        });
    };
    /* loadEquityData() {
      console.log("Loading data into DB");
      this.stockservice.loadEquity().subscribe(data => {
      });
    }; */
    EquityComponent.prototype.getEquities = function () {
        var _this = this;
        this.search.filter = this.filtersRequest;
        this.stockservice.getEquityByFilter(this.search)
            .subscribe(function (data) {
            if (_this.search.type == 'CALL') {
                _this.callEquities = data;
            }
            else
                _this.putEquities = data;
        });
        console.log("Loading Call data" + this.callEquities);
        console.log("Loading Put data " + this.putEquities);
    };
    EquityComponent.prototype.checkValue = function (event, obj, type) {
        this.search.type = type;
        if (event) {
            this.filtersRequest.push(obj);
        }
        else {
            this.filtersRequest = this.filtersRequest.filter(function (item) { return item !== obj; });
        }
        this.getEquities();
    };
    /* symbolDropDown(symbol: any) {
      this.search.symbol = symbol;
      this.getEquities();
    } */
    /* getSymbols() {
      this.stockservice.getSymbols().subscribe(data => {
        this.symbols = data;
      });
    } */
    EquityComponent.prototype.onStrikeChange = function (value) {
        this.search.strikePrice = value;
        this.getEquities();
    };
    EquityComponent.prototype.onDateChange = function (value, isStartDate) {
        if (isStartDate) {
            this.search.startDate = value;
        }
        else
            this.search.endDate = value;
        //this.getEquities();
    };
    EquityComponent.prototype.getData = function (value) {
        this.search.type = value;
        this.getEquities();
    };
    EquityComponent.prototype.callSortBy = function (sortBy, sortDir) {
        this.sortDir = sortDir;
        if (this.sortDir) {
            this.callEquities.sort(function (a, b) {
                return a[sortBy] - b[sortBy];
            });
        }
        else {
            this.callEquities.sort(function (a, b) {
                return b[sortBy] - a[sortBy];
            });
        }
    };
    EquityComponent.prototype.putSortBy = function (sortBy, sortDir) {
        this.sortDir = sortDir;
        if (this.sortDir) {
            this.putEquities.sort(function (a, b) {
                return a[sortBy] - b[sortBy];
            });
        }
        else {
            this.putEquities.sort(function (a, b) {
                return b[sortBy] - a[sortBy];
            });
        }
    };
    EquityComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-equity',
            template: __webpack_require__(/*! ./equity.component.html */ "./src/app/equity/equity.component.html"),
            styles: [__webpack_require__(/*! ./equity.component.css */ "./src/app/equity/equity.component.css")]
        }),
        __metadata("design:paramtypes", [_service_stock_service__WEBPACK_IMPORTED_MODULE_1__["StockService"]])
    ], EquityComponent);
    return EquityComponent;
}());



/***/ }),

/***/ "./src/app/list-stocks/list-stocks.component.html":
/*!********************************************************!*\
  !*** ./src/app/list-stocks/list-stocks.component.html ***!
  \********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"stock-pan\">\n  <div class=\"row\">\n    <div class=\"col-md-6 \">\n      <h2> Stock Details</h2>\n    </div>\n    <!-- <div class=\"col-md-6 seperator\">\n      <div class=\"col-md-2\">\n        <button class=\"btn btn-danger\" (click)=\"loadData()\"> Import Data</button>\n      </div>\n    </div> -->\n  </div>\n\n  <div class=\"row seperator\">\n    <div class=\"col-md-12\">\n      <div class=\"row seperator\">\n        <div class=\"col-md-2\">\n          <label for=\"start\">Start date:</label>\n          <input class=\"form-control\" type=\"date\" id=\"start\" name=\"startDate\"\n            (change)=\"onDateChange($event.target.value,true)\" />\n        </div>\n        <div class=\"col-md-2\">\n          <label for=\"start\">End date:</label>\n          <input class=\"form-control\" type=\"date\" id=\"end\" name=\"endDate\"\n            (change)=\"onDateChange($event.target.value,false)\">\n        </div>\n        <div class=\"col-md-2\">\n            <button class=\"btn btn-default\" (click)=\"getAllStocksDataList()\"> Search</button>\n          </div>\n      </div>\n    </div>\n  </div>\n  <div class=\"row\">\n    <div class=\"col-md-12\">\n      <div class=\"panel panel-default\">\n        <div class=\"panel-body\">\n          Note <br><b>ON :</b> Greater value<br>\n          <b>OFF : </b> Less value\n\n          <div class=\"row\">\n            <div class=\"col-md-6\" *ngFor=\"let chk of checkBoxFilter\" class=\"checkbox\"\n              style=\"margin-left:10px; margin-right:10px;\">\n              <label><input type=\"checkbox\" (change)=\"checkValue($event.target.checked,chk)\">{{chk.name}}</label>\n              <label class=\"switch\" style=\"margin-left:10px;margin-top: 9px;\">\n                <input type=\"checkbox\" checked (change)=\"directionValue($event.target.checked,chk)\">\n                <span class=\"slider round\"></span>\n              </label>\n            </div>\n          </div>\n          <div class=\"row\">\n            <div class=\"form-group\">\n            </div>\n          </div>\n        </div>\n      </div>\n    </div>\n  </div>\n  <div class=\"row\">\n    <label>Total stocks : {{stocks.length}}</label>\n    <div class=\"col-md-12\">\n      <table class=\"table table-striped\">\n        <thead>\n          <tr>\n            <th class=\"hidden\">Date</th>\n            <th (click)=\"sortBy('name',!sortDir)\">Name</th>\n            <th class=\"sort-arrow\" (click)=\"sortBy('closePrice',!sortDir)\">Close Price</th>\n            <th class=\"sort-arrow\" (click)=\"sortBy('ttlTradeQty',!sortDir)\">Trade Qty</th>\n            <th class=\"sort-arrow\" (click)=\"sortBy('deliveryQty',!sortDir)\">Del Qty</th>\n            <th class=\"sort-arrow\" (click)=\"sortBy('deliveryPer',!sortDir)\">Del Per</th>\n          </tr>\n        </thead>\n        <tbody>\n          <tr *ngFor=\"let stock of stocks;let i = index\">\n            <td>{{stock.date| date: 'dd/MM/yyyy'}}</td>\n            <td>{{stock.name}}</td>\n            <td>{{stock.closePrice}}</td>\n            <td>{{stock.ttlTradeQty}}</td>\n            <td>{{stock.deliveryQty}}</td>\n            <td>{{stock.deliveryPer}}</td>\n          </tr>\n        </tbody>\n      </table>\n    </div>\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/list-stocks/list-stocks.component.ts":
/*!******************************************************!*\
  !*** ./src/app/list-stocks/list-stocks.component.ts ***!
  \******************************************************/
/*! exports provided: ListStocksComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ListStocksComponent", function() { return ListStocksComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _service_stock_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../service/stock.service */ "./src/app/service/stock.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ListStocksComponent = /** @class */ (function () {
    function ListStocksComponent(router, stockservice) {
        this.router = router;
        this.stockservice = stockservice;
        this.stocks = [];
        this.filter = [];
    }
    ListStocksComponent.prototype.ngOnInit = function () {
        this.filter = [];
        this.checkBox = [
            { key: "CLOSE_PRICE", value: 0, title: "Close Price" },
            { key: "TRADE_QTY", value: 1, title: "TT Trade Qty" }
        ];
        this.getCheckfilter();
    };
    ListStocksComponent.prototype.getStocks = function () {
        var _this = this;
        this.stockservice.getStocksByFilter(this.startDate, this.endDate, this.filter)
            .subscribe(function (data) {
            _this.stocks = data;
        });
        console.log(this.stocks);
    };
    ListStocksComponent.prototype.getAllStocksDataList = function () {
        var _this = this;
        this.stockservice.getAllStocksDataList(this.startDate, this.endDate, this.filter)
            .subscribe(function (data) {
            _this.stocks = data;
        });
        console.log(this.stocks);
    };
    ListStocksComponent.prototype.loadData = function () {
        var _this = this;
        console.log("Loading data into DB");
        this.stockservice.loadData().subscribe(function (data) {
            _this.stocks = data;
        });
        //this.router.navigate(['add-user']);
    };
    ;
    ListStocksComponent.prototype.getCheckfilter = function () {
        var _this = this;
        this.stockservice.getFilters("filter").subscribe(function (data) {
            _this.checkBoxFilter = data;
        });
    };
    ListStocksComponent.prototype.checkValue = function (event, obj) {
        if (event) {
            this.filter.push(obj);
        }
        else {
            this.filter = this.filter.filter(function (item) { return item !== obj; });
        }
        this.getStocks();
    };
    ListStocksComponent.prototype.directionValue = function (event, obj) {
        if (this.filter.length > 0) {
            var filtr = void 0;
            filtr = this.filter.find(function (item) { return item == obj; });
            if (event) {
                filtr.direction = 'UP';
            }
            else {
                filtr.direction = 'DOWN';
            }
            this.filter = this.filter.filter(function (item) { return item !== obj; });
            this.filter.push(filtr);
            this.getStocks();
        }
    };
    ListStocksComponent.prototype.sortBy = function (sortBy, sortDir) {
        this.sortDir = sortDir;
        if (this.sortDir) {
            this.stocks.sort(function (a, b) {
                return a[sortBy] - b[sortBy];
            });
        }
        else {
            this.stocks.sort(function (a, b) {
                return b[sortBy] - a[sortBy];
            });
        }
    };
    ListStocksComponent.prototype.onDateChange = function (value, isStartDate) {
        if (isStartDate) {
            this.startDate = value;
        }
        else
            this.endDate = value;
    };
    ListStocksComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-list-stock',
            template: __webpack_require__(/*! ./list-stocks.component.html */ "./src/app/list-stocks/list-stocks.component.html"),
            styles: [__webpack_require__(/*! ./list.stock.css */ "./src/app/list-stocks/list.stock.css")]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_1__["Router"], _service_stock_service__WEBPACK_IMPORTED_MODULE_2__["StockService"]])
    ], ListStocksComponent);
    return ListStocksComponent;
}());



/***/ }),

/***/ "./src/app/list-stocks/list.stock.css":
/*!********************************************!*\
  !*** ./src/app/list-stocks/list.stock.css ***!
  \********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\r\n.seperator {\r\nmargin-top: 10px;\r\n}\r\nlabel {\r\n    font: bold;\r\n}\r\n.sort-arrow{\r\n    cursor: pointer;\r\n}\r\n.switch {\r\n    position: relative;\r\n    display: inline-block;\r\n    width: 35px;\r\n    height: 15px;\r\n  }\r\n.switch input { \r\n    opacity: 0;\r\n    width: 0;\r\n    height: 0;\r\n  }\r\n.slider {\r\n    position: absolute;\r\n    cursor: pointer;\r\n    top: 0;\r\n    left: 0;\r\n    right: 0;\r\n    bottom: 0;\r\n    background-color: #ccc;\r\n    transition: .4s;\r\n  }\r\n.slider:before {\r\n    position: absolute;\r\n    content: \"\";\r\n    height: 15px;\r\n    width: 15px;\r\n    left: -6px;\r\n    bottom: 0px;\r\n    background-color: white;\r\n    transition: .4s;\r\n  }\r\ninput:checked + .slider {\r\n    background-color: #2196F3;\r\n  }\r\ninput:focus + .slider {\r\n    box-shadow: 0 0 1px #2196F3;\r\n  }\r\ninput:checked + .slider:before {\r\n    -webkit-transform: translateX(26px);\r\n    transform: translateX(26px);\r\n  }\r\n/* Rounded sliders */\r\n.slider.round {\r\n    border-radius: 34px;\r\n  }\r\n.slider.round:before {\r\n    border-radius: 50%;\r\n  }"

/***/ }),

/***/ "./src/app/menu/menu.component.css":
/*!*****************************************!*\
  !*** ./src/app/menu/menu.component.css ***!
  \*****************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "body {margin:0;}\r\n\r\nul {\r\n  list-style-type: none;\r\n  margin: 0;\r\n  padding: 0;\r\n  overflow: hidden;\r\n  background-color: #333;\r\n  position: fixed;\r\n  top: 0;\r\n  width: 100%;\r\n}\r\n\r\nli {\r\n  float: left;\r\n}\r\n\r\nli a {\r\n  display: block;\r\n  color: white;\r\n  text-align: center;\r\n  padding: 14px 16px;\r\n  text-decoration: none;\r\n}\r\n\r\nli a:hover:not(.active) {\r\n  background-color: #111;\r\n}\r\n\r\n.active {\r\n  background-color: #cc4e82;\r\n}"

/***/ }),

/***/ "./src/app/menu/menu.component.html":
/*!******************************************!*\
  !*** ./src/app/menu/menu.component.html ***!
  \******************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<ul class=\"active\">\n    <li><a  href=\"#home\">Home</a></li>\n    <li><a routerLink=\"/stock\">Stock Data</a></li>\n    <li><a routerLink=\"/stock-opitons-chain\">Stock Option Chain Data</a></li>\n    <li><a routerLink=\"/equity\">Option Chain Data</a></li>\n    <li><a routerLink=\"/premium-dk\">Today-Yesterday EOD</a></li>\n    <li><a routerLink=\"/exchange-activity\">FII's Data</a></li>\n    <!-- <li><a routerLink=\"/all-data-download\">All Data Download</a></li> -->\n  </ul>\n"

/***/ }),

/***/ "./src/app/menu/menu.component.ts":
/*!****************************************!*\
  !*** ./src/app/menu/menu.component.ts ***!
  \****************************************/
/*! exports provided: MenuComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MenuComponent", function() { return MenuComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var MenuComponent = /** @class */ (function () {
    function MenuComponent() {
    }
    MenuComponent.prototype.ngOnInit = function () {
    };
    MenuComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-menu',
            template: __webpack_require__(/*! ./menu.component.html */ "./src/app/menu/menu.component.html"),
            styles: [__webpack_require__(/*! ./menu.component.css */ "./src/app/menu/menu.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], MenuComponent);
    return MenuComponent;
}());



/***/ }),

/***/ "./src/app/premiumdk/premiumdk.component.css":
/*!***************************************************!*\
  !*** ./src/app/premiumdk/premiumdk.component.css ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".strikethrough {\r\n    text-decoration: line-through;\r\n}"

/***/ }),

/***/ "./src/app/premiumdk/premiumdk.component.html":
/*!****************************************************!*\
  !*** ./src/app/premiumdk/premiumdk.component.html ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"stock-pan\">\n  <div class=\"row\">\n    <div class=\"col-md-10 \">\n      <h2> Today - Yesterday EOD.</h2>\n    </div>\n  </div>\n  <div class=\"row seperator\">\n    <div class=\"col-md-12\">\n      <div class=\"row seperator\">\n      </div>\n    </div>\n  </div>\n  <div class=\"row seperator\">\n    <div class=\"col-md-2\">\n      <label for=\"start\">Start date:</label>\n      <input class=\"form-control\" type=\"date\" id=\"start\" name=\"startDate\"\n        (change)=\"onDateChange($event.target.value,true)\" />\n    </div>\n    <div class=\"col-md-2\">\n      <label for=\"start\">End date:</label>\n      <input class=\"form-control\" type=\"date\" id=\"end\" name=\"endDate\"\n        (change)=\"onDateChange($event.target.value,false)\">\n    </div>\n  </div>\n  <div class=\"row\">\n    <div class=\"col-md-12\">\n      <div class=\"row\">\n        <div class=\"col-md-12\">\n          <div *ngFor=\"let chk of checkBoxFilter\" class=\"checkbox check-container\"\n            style=\"margin-left:10px; margin-right:10px;\">\n            <label><input type=\"checkbox\" (change)=\"checkValue($event.target.checked,chk,'CALL')\">{{chk.name}}</label>\n            <!-- <label class=\"switch\" style=\"margin-left:10px;margin-top: 9px;\">\n              <input type=\"checkbox\" checked (change)=\"directionValue($event.target.checked,chk)\">\n              <span class=\"slider round\"></span>\n            </label> -->\n          </div>\n        </div>\n        <div class=\"col-md-12\">\n          <div class=\"row\">\n            <div class=\"col-md-10\">\n            </div>\n          </div>\n          <table id=\"equityTable\">\n            <tr>\n              <th>Date</th>\n              <th>OI</th>\n              <th>ChangeIn OI</th>\n              <th>IV</th>\n              <th>LTP</th>\n              <th>Net Change</th>\n              <th>Volume</th>\n              <th>Strike Price</th>\n            </tr>\n            <tr *ngFor=\"let equity of equities;let i = index\">\n              <td>{{equity.date| date: 'dd/MM/yyyy'}}</td>\n              <td><label class=\"strikethrough\">{{equity.oi}}</label>\n                <label *ngIf=\"equity.prevEquity!==null\">{{equity.prevEquity.oi}}</label>\n              </td>\n              <td><label class=\"strikethrough\">{{equity.chnginOI}}</label>\n                <label *ngIf=\"equity.prevEquity!==null\">{{equity.prevEquity.chnginOI}}</label>\n              </td>\n              <td><label class=\"strikethrough\">{{equity.iv}}</label>\n                <label *ngIf=\"equity.prevEquity!==null\">{{equity.prevEquity.iv}}</label></td>\n              <td><label class=\"strikethrough\">{{equity.ltp}}</label>\n                <label *ngIf=\"equity.prevEquity!==null\">{{equity.prevEquity.ltp| number : '.0-4'}}</label></td>\n              <td>{{equity.netChng| number : '.4-4'}}</td>\n              <td>{{equity.volume}}</td>\n              <td>{{equity.strikePrice}}</td>\n            </tr>\n          </table>\n        </div>\n      </div>\n    </div>\n    </div>\n</div>"

/***/ }),

/***/ "./src/app/premiumdk/premiumdk.component.ts":
/*!**************************************************!*\
  !*** ./src/app/premiumdk/premiumdk.component.ts ***!
  \**************************************************/
/*! exports provided: PremiumdkComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PremiumdkComponent", function() { return PremiumdkComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _service_stock_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../service/stock.service */ "./src/app/service/stock.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var PremiumdkComponent = /** @class */ (function () {
    function PremiumdkComponent(stockService) {
        this.stockService = stockService;
        this.checkBoxFilter = [];
        this.equities = [];
        this.search = { strikePrice: null, startDate: null, endDate: null, type: 'CALL' };
        this.filtersRequest = [];
    }
    PremiumdkComponent.prototype.ngOnInit = function () {
        this.getCheckfilter();
    };
    PremiumdkComponent.prototype.getCheckfilter = function () {
        var _this = this;
        this.stockService.getFilters("equityFilter").subscribe(function (data) {
            _this.checkBoxFilter = data;
        });
    };
    PremiumdkComponent.prototype.getYesterdayMinusToday = function () {
        var _this = this;
        this.search.filter = this.filtersRequest;
        this.stockService.getYesterdayMinusTodayByFilter(this.search)
            .subscribe(function (data) {
            _this.equities = data;
        });
    };
    PremiumdkComponent.prototype.checkValue = function (event, obj, type) {
        this.search.type = type;
        if (event) {
            this.filtersRequest.push(obj);
        }
        else {
            this.filtersRequest = this.filtersRequest.filter(function (item) { return item !== obj; });
        }
        this.getYesterdayMinusToday();
    };
    PremiumdkComponent.prototype.onDateChange = function (value, isStartDate) {
        if (isStartDate) {
            this.search.startDate = value;
        }
        else
            this.search.endDate = value;
        // this.getEquities();
        this.getYesterdayMinusToday();
    };
    PremiumdkComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-premiumdk',
            template: __webpack_require__(/*! ./premiumdk.component.html */ "./src/app/premiumdk/premiumdk.component.html"),
            styles: [__webpack_require__(/*! ./premiumdk.component.css */ "./src/app/premiumdk/premiumdk.component.css")]
        }),
        __metadata("design:paramtypes", [_service_stock_service__WEBPACK_IMPORTED_MODULE_1__["StockService"]])
    ], PremiumdkComponent);
    return PremiumdkComponent;
}());



/***/ }),

/***/ "./src/app/service/auth.service.ts":
/*!*****************************************!*\
  !*** ./src/app/service/auth.service.ts ***!
  \*****************************************/
/*! exports provided: AuthenticationService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthenticationService", function() { return AuthenticationService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AuthenticationService = /** @class */ (function () {
    function AuthenticationService(http) {
        this.http = http;
    }
    AuthenticationService.prototype.login = function (username, password) {
        return this.http.post('/api/authenticate', { username: username, password: password })
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_2__["map"])(function (user) {
            // login successful if there's a jwt token in the response
            if (user && user.token) {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('currentUser', JSON.stringify(user));
            }
            return user;
        }));
    };
    AuthenticationService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])(),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"]])
    ], AuthenticationService);
    return AuthenticationService;
}());



/***/ }),

/***/ "./src/app/service/stock.service.ts":
/*!******************************************!*\
  !*** ./src/app/service/stock.service.ts ***!
  \******************************************/
/*! exports provided: StockService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "StockService", function() { return StockService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var StockService = /** @class */ (function () {
    function StockService(http) {
        this.http = http;
        this.baseUrl = 'http://localhost:8080/';
        this.equityEndPoint = 'options/';
    }
    // Stock service...........!
    StockService.prototype.loadData = function () {
        return this.http.get(this.baseUrl + 'stock/load-stock');
    };
    StockService.prototype.getStocksByFilter = function (startDate, endDate, filetr) {
        var params = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpParams"]();
        params = params.append("endDate", endDate);
        params = params.append("startDate", startDate);
        //params = params.append("filter", filetr.toString());
        return this.http.post(this.baseUrl + 'stock/search', filetr, { params: params });
    };
    StockService.prototype.getAllStocksDataList = function (startDate, endDate, filetr) {
        var params = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpParams"]();
        params = params.append("endDate", endDate);
        params = params.append("startDate", startDate);
        //params = params.append("filter", filetr.toString());
        return this.http.post(this.baseUrl + 'stock/get-all-stock-list', filetr, { params: params });
    };
    StockService.prototype.getFilters = function (type) {
        var params = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpParams"]();
        params = params.append("type", type);
        return this.http.get(this.baseUrl + 'stock/get-filter', { params: params });
    };
    //Service for Equity Component
    StockService.prototype.loadEquity = function () {
        return this.http.get(this.baseUrl + this.equityEndPoint + 'load-nifty');
    };
    StockService.prototype.getEquityByFilter = function (search) {
        var params = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpParams"]();
        return this.http.post(this.baseUrl + this.equityEndPoint + 'search/nifty', search);
    };
    // Service for Yesterday - Today
    StockService.prototype.getYesterdayMinusTodayByFilter = function (search) {
        var params = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpParams"]();
        return this.http.post(this.baseUrl + this.equityEndPoint + 'nifty/yesterday-today', search);
    };
    //Service for Activity Component
    StockService.prototype.loadActivityData = function () {
        return this.http.get(this.baseUrl + 'activity/load');
    };
    StockService.prototype.getActivity = function (startDate, endDate, filter) {
        var params = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpParams"]();
        params = params.append("endDate", endDate);
        params = params.append("startDate", startDate);
        return this.http.post(this.baseUrl + 'activity/search', params);
    };
    //Stock Options Services..!
    StockService.prototype.loadStockOptions = function () {
        return this.http.get(this.baseUrl + this.equityEndPoint + 'load-stocksOptions');
    };
    StockService.prototype.getSymbols = function () {
        return this.http.get(this.baseUrl + 'stock/symbol');
    };
    StockService.prototype.getStocksOptionsByFilter = function (search) {
        var params = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpParams"]();
        return this.http.post(this.baseUrl + this.equityEndPoint + 'search/stocksOptions', search);
    };
    StockService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])(),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"]])
    ], StockService);
    return StockService;
}());



/***/ }),

/***/ "./src/app/stock-option-chain/stock-option-chain.component.css":
/*!*********************************************************************!*\
  !*** ./src/app/stock-option-chain/stock-option-chain.component.css ***!
  \*********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/stock-option-chain/stock-option-chain.component.html":
/*!**********************************************************************!*\
  !*** ./src/app/stock-option-chain/stock-option-chain.component.html ***!
  \**********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"stock-pan\">\n    <div class=\"row\">\n      <div class=\"col-md-10 \">\n        <h2> Stock Option Chain.</h2>\n      </div>\n    </div>\n    <div class=\"row seperator\">\n      <div class=\"col-md-12\">\n        <div class=\"row seperator\">\n        </div>\n      </div>\n    </div>\n    <div class=\"row seperator\">\n      <div class=\"col-md-3\">\n        <label for=\"days\">Symbol:</label>\n        <select class=\"form-control\" (change)=\"symbolDropDown($event.target.value)\">\n           <option *ngFor=\"let symbol of symbols\" value={{symbol.symbol}}>\n            {{symbol.companyName}}\n          </option>\n        </select>\n      </div>\n      <div class=\"col-md-2\">\n        <label for=\"start\">Start date:</label>\n        <input class=\"form-control\" type=\"date\" id=\"start\" name=\"startDate\"\n          (change)=\"onDateChange($event.target.value,true)\" />\n      </div>\n      <div class=\"col-md-2\">\n        <label for=\"start\">End date:</label>\n        <input class=\"form-control\" type=\"date\" id=\"end\" name=\"endDate\"\n          (change)=\"onDateChange($event.target.value,false)\">\n      </div>\n    </div>\n    <div class=\"row seperator\"></div>\n    <div class=\"row\">\n      <div class=\"col-md-6\">\n        <div class=\"row\">\n          <div class=\"col-md-12\">\n            <div *ngFor=\"let chk of checkBoxFilter\" class=\"checkbox check-container\"\n              style=\"margin-left:10px; margin-right:10px;\">\n              <label><input type=\"checkbox\" (change)=\"checkValue($event.target.checked,chk,'CALL')\">{{chk.name}}</label>\n            </div>\n          </div>\n          <div class=\"col-md-12\">\n            <div class=\"row\">\n              <div class=\"col-md-10\">\n                <h4>Call</h4>\n              </div>\n              <div class=\"col-md-1\">\n                <button class=\"btn btn-info\" (click)=\"getData('CALL')\"> Get Call</button>\n              </div>\n            </div>\n            <div class=\"row seperator\"></div>\n            <table id=\"customers\">\n              <tr>\n                <th >Date</th>\n                <th class=\"sort-arrow\" (click)=\"callSortBy('oi',!sortDir)\">OI</th>\n                <th class=\"sort-arrow\" (click)=\"callSortBy('chnginOI',!sortDir)\">COI</th>\n                <th>IV</th>\n                <th>LTP</th>\n                <th>Net-C</th>\n                <th>Vol</th>\n                <th class=\"sort-arrow\" (click)=\"callSortBy('strikePrice',!sortDir)\">SP</th>\n                <th class=\"sort-arrow\" (click)=\"callSortBy('postionsVol',!sortDir)\">CHI/VOL</th>\n              </tr>\n              <tr *ngFor=\"let equity of callEquities;let i = index\">\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.date| date: 'dd/MM/yy'}}</td>\n                <td  *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.oi}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.chnginOI}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.iv}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.ltp}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.netChng}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.volume}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.strikePrice}}</td>\n                <!-- <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.chnginOI / equity.volume | number:'1.1-1'}}</td> -->\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.postionsVol | number:'1.1-1'}}</td>\n              </tr>\n            </table>\n          </div>\n        </div>\n      </div>\n      <div class=\"col-md-6\">\n        <div class=\"row\">\n          <div class=\"col-md-12\">\n            <div *ngFor=\"let chk of checkBoxFilter\" class=\"checkbox check-container\"\n              style=\"margin-left:10px; margin-right:10px;\">\n              <label><input type=\"checkbox\" (change)=\"checkValue($event.target.checked,chk,'PUT')\">{{chk.name}}</label>\n            </div>\n          </div>\n          <div class=\"col-md-12\">\n            <div class=\"row\">\n              <div class=\"col-md-10\">\n                <h4>PUT</h4>\n              </div>\n              <div class=\"col-md-1\">\n                <button class=\"btn btn-info\" (click)=\"getData('PUT')\"> Get Put</button>\n              </div>\n            </div>\n            <div class=\"row seperator\"></div>\n            <table id=\"customers\">\n              <tr>\n                <th class=\"sort-arrow\" (click)=\"putSortBy('postionsVol',!sortDir)\">CHI/VOL</th>\n                <th>Date</th>\n                <th  class=\"sort-arrow\" (click)=\"putSortBy('oi',!sortDir)\">OI</th>\n                <th class=\"sort-arrow\" (click)=\"putSortBy('chnginOI',!sortDir)\">C-OI</th>\n                <th>IV</th>\n                <th>LTP</th>\n                <th>Net-C</th>\n                <th>Vol</th>\n                <th class=\"sort-arrow\" (click)=\"putSortBy('strikePrice',!sortDir)\">SP</th>\n              </tr>\n              <tr *ngFor=\"let equity of putEquities;let i = index\">\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.postionsVol | number:'1.1-1'}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.date | date: 'dd/MM/yy'}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.oi}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.chnginOI}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.iv}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.ltp}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.netChng}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.volume}}</td>\n                <td *ngIf=\"equity.oi >0  && equity.iv > 0\">{{equity.strikePrice}}</td>\n              </tr>\n            </table>\n          </div>\n        </div>\n      </div>\n    </div>\n  </div>"

/***/ }),

/***/ "./src/app/stock-option-chain/stock-option-chain.component.ts":
/*!********************************************************************!*\
  !*** ./src/app/stock-option-chain/stock-option-chain.component.ts ***!
  \********************************************************************/
/*! exports provided: StockOptionChainComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "StockOptionChainComponent", function() { return StockOptionChainComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _service_stock_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../service/stock.service */ "./src/app/service/stock.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var StockOptionChainComponent = /** @class */ (function () {
    function StockOptionChainComponent(stockservice) {
        this.stockservice = stockservice;
        this.equities = [];
        this.symbols = [];
        this.filtersRequest = [];
        this.callEquities = [];
        this.putEquities = [];
        this.search = { strikePrice: null, startDate: null, endDate: null, type: 'CALL' };
    }
    StockOptionChainComponent.prototype.ngOnInit = function () {
        this.getCheckfilter();
        this.getSymbols();
        this.getStockOptions();
    };
    StockOptionChainComponent.prototype.getCheckfilter = function () {
        var _this = this;
        this.stockservice.getFilters("equityFilter").subscribe(function (data) {
            _this.checkBoxFilter = data;
        });
    };
    StockOptionChainComponent.prototype.getSymbols = function () {
        var _this = this;
        this.stockservice.getSymbols().subscribe(function (data) {
            _this.symbols = data;
        });
    };
    StockOptionChainComponent.prototype.onDateChange = function (value, isStartDate) {
        if (isStartDate) {
            this.search.startDate = value;
        }
        else
            this.search.endDate = value;
        // this.getStockOptions();
    };
    StockOptionChainComponent.prototype.checkValue = function (event, obj, type) {
        this.search.type = type;
        if (event) {
            this.filtersRequest.push(obj);
        }
        else {
            this.filtersRequest = this.filtersRequest.filter(function (item) { return item !== obj; });
        }
        this.getStockOptions();
    };
    StockOptionChainComponent.prototype.getData = function (value) {
        this.search.type = value;
        this.getStockOptions();
    };
    StockOptionChainComponent.prototype.callSortBy = function (sortBy, sortDir) {
        this.sortDir = sortDir;
        if (this.sortDir) {
            this.callEquities.sort(function (a, b) {
                return a[sortBy] - b[sortBy];
            });
        }
        else {
            this.callEquities.sort(function (a, b) {
                return b[sortBy] - a[sortBy];
            });
        }
    };
    StockOptionChainComponent.prototype.putSortBy = function (sortBy, sortDir) {
        this.sortDir = sortDir;
        if (this.sortDir) {
            this.putEquities.sort(function (a, b) {
                return a[sortBy] - b[sortBy];
            });
        }
        else {
            this.putEquities.sort(function (a, b) {
                return b[sortBy] - a[sortBy];
            });
        }
    };
    StockOptionChainComponent.prototype.getStockOptions = function () {
        var _this = this;
        this.search.filter = this.filtersRequest;
        this.stockservice.getStocksOptionsByFilter(this.search)
            .subscribe(function (data) {
            if (_this.search.type == 'CALL') {
                _this.callEquities = data;
            }
            else
                _this.putEquities = data;
        });
        console.log("Loading Call data" + this.callEquities);
        console.log("Loading Put data " + this.putEquities);
    };
    StockOptionChainComponent.prototype.symbolDropDown = function (symbol) {
        this.search.symbol = symbol;
        //this.getEquities();
    };
    StockOptionChainComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-stock-option-chain',
            template: __webpack_require__(/*! ./stock-option-chain.component.html */ "./src/app/stock-option-chain/stock-option-chain.component.html"),
            styles: [__webpack_require__(/*! ./stock-option-chain.component.css */ "./src/app/stock-option-chain/stock-option-chain.component.css")]
        }),
        __metadata("design:paramtypes", [_service_stock_service__WEBPACK_IMPORTED_MODULE_1__["StockService"]])
    ], StockOptionChainComponent);
    return StockOptionChainComponent;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
var environment = {
    production: false
};
/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm5/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");




if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! C:\stock-reader-frontend-master\src\main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map