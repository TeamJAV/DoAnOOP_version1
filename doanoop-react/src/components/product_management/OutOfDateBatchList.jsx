import React, { Component } from "react";
import { isArrayNull } from "../../utils/array";

class OutOfDateBatchList extends Component {
  state = {
    batches: [],
  };

  componentDidMount() {
    this.getOutOfDateBatches();
  }

  getOutOfDateBatches = () => {
    fetch(`http://localhost:8081/product/outofdate`, {
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        this.setState({ batches: data }, () => {
          console.log(this.state.batches);
        });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  renderBatches = () => {
    if (isArrayNull(this.state.batches)) {
      return null;
    }
    let key = 0;
    let products = this.state.batches.map((result) => {
      return (
        <tr data-key={key} key={key++}>
          <th scope="row">{key}</th>
          <td>{result.sku}</td>
          <td>{result.products.name}</td>
          <td>{result.importInvoice.importDate}</td>
          <td>{result.expiredDate}</td>
        </tr>
      );
    });
    return products;
  };

  render() {
    return (
      <>
        <div className="suppliers-container">
          <table className="table">
            <thead>
              <tr>
                <th scope="col">STT</th>
                <th scope="col">Mã SKU</th>
                <th scope="col">Tên mặt hàng</th>
                <th scope="col">Ngày nhập</th>
                <th scope="col">Ngày hết hạn</th>
              </tr>
            </thead>
            <tbody>{this.renderBatches()}</tbody>
          </table>
        </div>
      </>
    );
  }
}

export default OutOfDateBatchList;
