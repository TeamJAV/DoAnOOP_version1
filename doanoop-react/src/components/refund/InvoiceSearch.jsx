import React, { Component } from "react";

export default class InvoiceSearch extends Component {
  constructor(props) {
    super(props);
    this.state = {
      value: "",
    };
  }

  handleFormSubmit = (event) => {
    event.preventDefault();
    fetch(`http://localhost:8081/refund/selling_invoice/${this.state.value}`, {
      method: "GET"
    })
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        this.props.setInvoice(data);
      })
      .catch((err) => {
        console.log(err)
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
            name="invoice-search__input"
            onChange={this.handleInputChange}
            value={this.state.value}
            placeholder="Nhập mã hóa đơn và nhấn enter để tìm kiếm..."
          ></input>
        </form>
      </>
    );
  }
}
