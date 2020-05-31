import React, { Component } from "react";
import BatchInputForm from "./BatchInputForm";

export default class BatchInputContainer extends Component {
  state = {
    productBatches: [],
  };

  setProductBatches = (batch) => {
    this.setState((prevState) => ({
      productBatches: [...prevState.productBatches, batch],
    }), () => {console.log(this.state.productBatches)});
  };

  render() {
    return (
      <>
        <BatchInputForm setBatches={this.setProductBatches} />
      </>
    );
  }
}
