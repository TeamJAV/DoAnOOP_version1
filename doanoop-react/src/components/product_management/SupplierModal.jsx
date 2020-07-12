import React, { Component } from "react";
import { Modal, Button } from "react-bootstrap";

class SupplierModal extends Component {
  handleSubmitSupplier = () => {};
  render() {
    return (
      <>
        <div>
          <Modal
            show={this.props.show}
            onHide={this.props.toggleModal}
            backdrop="static"
            animation={false}
            centered={true}
            className="supplier-modal"
          >
            <Modal.Body>
              <form
                onSubmit={this.handleSubmitSupplier}
                className="price-modal-content"
                id="supplier-modal-form"
              >
                <div className="price-modal-content-left">
                  <div>Tên nhà cung cấp:</div>
                  <div>Địa chỉ</div>
                  <div>Số điện thoại:</div>
                </div>
                <div>
                  <input
                    type="text"
                    className="supplier-input"
                    id="supName"
                    name="name"
                    value={this.props.getSupplierState("name")}
                    onChange={this.props.setSupplier}
                  />
                  <input
                    type="text"
                    className="supplier-input"
                    id="supAddress"
                    name="address"
                    value={this.props.getSupplierState("address")}
                    onChange={this.props.setSupplier}
                  />
                  <input
                    type="text"
                    className="supplier-input"
                    id="supPhone"
                    name="tel"
                    value={this.props.getSupplierState("tel")}
                    onChange={this.props.setSupplier}
                  />
                </div>
              </form>
            </Modal.Body>
            <Modal.Footer>
              <div>
                <button
                  className="btn-type-2 btn-confirm"
                  onClick={() => {
                    this.props.fetchSupplier();
                  }}
                >
                  {this.props.type === "create" ? "Tạo mới" : "Cập nhật"}
                </button>
                <Button
                  className="btn btn-secondary btn-cancel"
                  onClick={() => {
                    this.props.resetSupplier();
                    this.props.toggleModal();
                  }}
                >
                  Quay lại
                </Button>
              </div>
            </Modal.Footer>
          </Modal>
        </div>
      </>
    );
  }
}

export default SupplierModal;
