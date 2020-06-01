import React, { Component } from "react";
import InvoiceContainer from "../components/refund/InvoiceContainer";
import NavigationBar from "../components/NavigationBar";

export default class RefundScreen extends Component {
  render() {
    return (
      <>
        <NavigationBar pathname="/refund"></NavigationBar>
        <InvoiceContainer></InvoiceContainer>
      </>
    );
  }
}
