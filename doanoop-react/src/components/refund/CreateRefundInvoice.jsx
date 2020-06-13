import React, { Component } from "react";
import { Button } from "react-bootstrap";

export default class CreateRefundInvoice extends Component {
  render() {
    return (
      <>
        <Button onClick={this.props.cancel}>Return</Button>
      </>
    );
  }
}
