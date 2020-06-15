import React, { Component } from "react";
import { sortByProductName } from "../../utils/array";

export default class InvoiceSearch extends Component {
  constructor(props) {
    super(props);
    this.state = {
      value: "",
    };
  }

  handleFormSubmit = (event) => {
    event.preventDefault();
    event.target.childNodes[0].blur();
    fetch(`http://localhost:8081/refund/create_refund_by/${this.state.value}`, {
      method: "GET",
    })
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        if (data.message) {
          this.props.setMessage(data.message);
        } else {
          data.invoiceDetail.sort((a, b) => {
            return sortByProductName(a.productBatches, b.productBatches);
          });
          this.props.setInvoice(data);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  handleInputChange = (event) => {
    this.setState({
      value: event.target.value,
    });
  };

  render() {
    return (
      <>
        <form className="invoice-search" onSubmit={this.handleFormSubmit}>
          <input
            type="text"
            className="invoice-search__input"
            onChange={this.handleInputChange}
            onFocus={() => {
              this.props.setInvoice({});
            }}
            value={this.state.value}
            placeholder="Nhập mã hóa đơn và nhấn enter..."
          ></input>
        </form>
      </>
    );
  }
}
