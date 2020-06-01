import React, { Component } from "react";
import { isArrayNull } from "../../utils/array";

export default class NewBatchList extends Component {
  handleDeleteBatch = (event) => {
    const batchIndex = event.target.parentNode.dataset.key - 1;
    this.props.deleteBatch(batchIndex);
  };

  renderListBatches = () => {
    if (isArrayNull(this.props.listBatches)) {
      return null;
    }
    let key = 0;
    let batch = this.props.listBatches.map((batch) => {
      return (
        <tr key={key++} data-key={key}>
          <th scope="row">{key}</th>
          <td>{batch.products.id}</td>
          <td>{batch.products.name}</td>
          <td>{batch.suppliers.name}</td>
          <td>{batch.expiredDate}</td>
          <td>{batch.importQuantity}</td>
          <td>{batch.importCost}</td>
          <td onClick={this.handleDeleteBatch}>Xóa</td>
        </tr>
      );
    });
    return batch;
  };

  render() {
    return (
      <>
        <div className="invoice-container">
          <table className="table">
            <thead>
              <tr>
                <th scope="col">STT</th>
                <th scope="col">Mã hàng</th>
                <th scope="col">Tên mặt hàng</th>
                <th scope="col">Nhà cung cấp</th>
                <th scope="col">Ngày hết hạn</th>
                <th scope="col">Số lượng</th>
                <th scope="col">Giá nhập</th>
                <th scope="col">#</th>
              </tr>
            </thead>
            <tbody>{this.renderListBatches()}</tbody>
          </table>
        </div>
      </>
    );
  }
}
