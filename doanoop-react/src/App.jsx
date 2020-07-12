import React from "react";
import "./App.css";
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";
import SellingScreen from "./pages/SellingScreen";
import ImportScreen from "./pages/ImportScreen";
import RefundScreen from "./pages/RefundScreen";
import TransactionScreen from "./pages/TransactionScreen";
import ProductManagementScreen from "./pages/ProductManagementScreen";

export default class App extends React.Component {
  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Redirect exact from="/" to="/selling"></Redirect>
          <Route path="/selling" component={SellingScreen}></Route>
          <Route path="/import" component={ImportScreen}></Route>
          <Route path="/refund" component={RefundScreen}></Route>
          <Route path="/trans-stats" component={TransactionScreen}></Route>
          <Route path="/product-management" component={ProductManagementScreen}></Route>
        </Switch>
      </BrowserRouter>
    );
  }
}
