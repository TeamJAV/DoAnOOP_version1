import React, { Component } from "react";
import NavigationBar from "../components/NavigationBar";
import ImportTab from "../components/import/ImportTab";
import "../style/css/importScreen.css";

export default class ImportScreen extends Component {
  render() {
    return (
      <>
        <NavigationBar pathname="/import"></NavigationBar>
        <ImportTab></ImportTab>
      </>
    );
  }
}
