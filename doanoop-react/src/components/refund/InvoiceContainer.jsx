import React, { Component } from "react";
import InvoiceSearch from "./InvoiceSearch";
import { Button } from "react-bootstrap";
import CreateRefundInvoice from "./CreateRefundInvoice";

export default class InvoiceContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      invoice: {},
      createNew: false,
    };
  }

  setCreateNew = () => {
    this.setState({
      createNew: !this.state.createNew,
    });
  };

  render() {
    return (
      <>
        {this.state.createNew ? (
          <CreateRefundInvoice return={this.setCreateNew}></CreateRefundInvoice>
        ) : (
          <>
            <InvoiceSearch />
            <Button onClick={this.setCreateNew}>Tạo hóa đơn trả</Button>
          </>
        )}
      </>
    );
  }
}
