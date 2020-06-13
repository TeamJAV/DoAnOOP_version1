import React, { Component } from "react";
import { isArrayNull } from "../utils/array.js";

export default class BatchList extends Component {
  renderInvoiceResult = () => {
    if (isArrayNull(this.props.list)) {
      return null;
    }
    let key = 0;
    let detail = this.props.list.map((result) => {
      const resultBatch = result.productBatches;
      return (
        <tr key={key++} data-sku={resultBatch.sku}>
          <th scope="row">{key}</th>
          <td>{resultBatch.sku}</td>
          <td>{resultBatch.products.name}</td>
          <td>{result.quantity}</td>
          <td>{resultBatch.products.price}</td>
          <td>{result.price}</td>
          {this.props.isDeleteAllowed ? (
            <td onClick={this.handleDeleteDetail}>Xóa</td>
          ) : null}
        </tr>
      );
    });
    return detail;
  };
  render() {
    return (
      <>
        <div className="invoice-container">
          <table className="table">
            <thead>
              <tr>
                <th scope="col">STT</th>
                <th scope="col">Mã SKU</th>
                <th scope="col">Tên sản phẩm</th>
                <th scope="col">Số lượng mua</th>
                <th scope="col">Đơn giá</th>
                <th scope="col">Thành tiền</th>
                {this.props.isDeleteAllowed ? (
                  <>
                    <th scope="col">#</th>
                  </>
                ) : null}
              </tr>
            </thead>
            <tbody>{this.renderInvoiceResult()}</tbody>
          </table>
        </div>
      </>
    );
  }
}
