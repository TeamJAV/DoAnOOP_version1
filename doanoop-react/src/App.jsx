import React from "react";
import "./App.css";
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";
import SellingScreen from "./pages/SellingScreen";
import ImportScreen from "./pages/ImportScreen";

export default class App extends React.Component {
  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Redirect exact from="/" to="/selling"></Redirect>
          <Route path="/selling" component={SellingScreen}></Route>
          <Route path="/import" component={ImportScreen}></Route>
        </Switch>
      </BrowserRouter>
    );
  }
}
