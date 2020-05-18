import React, { Component } from "react";
import { Modal, Button } from "react-bootstrap";

export default class ConfirmModal extends Component {
  
  renderModalContent = () => {
    switch (this.props.type) {
      case "selling-confirm":
        return "Xác nhận hóa đơn đã được thanh toán?";
      case "selling-fetching":
        return "Hoá đơn đang được xử lý. Xin vui lòng chờ trong giây lát ...";
      case "selling-success":
        return "Hóa đơn đã được xử lý thành công. Bấm hoàn tất để kết thúc";
      case "selling-failure":
        return "Đã xảy ra lỗi. Xin vui lòng thử lại";
      default:
        return null;
    }
  };

  renderModalButton = () => {
    switch (this.props.type) {
      case "selling-confirm":
        return (
          <>
            <Button variant="secondary" onClick={this.props.toggleModal}>
              Hủy bỏ
            </Button>
            <Button variant="primary" onClick={this.props.saveInvoice}>
              Đồng ý
            </Button>
          </>
        );
      case "selling-fetching":
        return (
          <>
            <div className="spinner-border text-secondary" role="status">
              <span className="sr-only">Loading...</span>
            </div>
          </>
        );
      case "selling-success":
        return (
          <>
            <Button variant="primary" href="selling">
              Hoàn tất
            </Button>
          </>
        );
      case "selling-failure":
        return "Đã xảy ra lỗi. Xin vui lòng thử lại";
      default:
        return null;
    }
  };

  render() {
    return (
      <>
        <Modal
          show={this.props.show}
          onHide={this.props.toggleModal}
          backdrop="static"
          animation={false}
        >
          <Modal.Body>{this.renderModalContent()}</Modal.Body>
          <Modal.Footer>{this.renderModalButton()}</Modal.Footer>
        </Modal>
      </>
    );
  }
}
