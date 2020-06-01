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
    console.log(this.state.value);
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
          ></input>
        </form>
      </>
    );
  }
}
