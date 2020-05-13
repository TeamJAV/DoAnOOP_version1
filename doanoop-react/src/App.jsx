import React from "react";
import "./App.css";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import HomeScreen from "./pages/HomeScreen.jsx";
import SellingScreen from "./pages/SellingScreen";

export default class App extends React.Component {
  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Route exact={true} path="/" component={HomeScreen}></Route>
          <Route path="/selling" component={SellingScreen}></Route>
        </Switch>
      </BrowserRouter>
    );
  }
}
