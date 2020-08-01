import React, { Component } from "react";
import NavigationBar from "../components/NavigationBar";
import ProductList from "../components/product_management/ProductList";
import { Tab, Row, Col, Nav } from "react-bootstrap";
import SupplierList from "../components/product_management/SupplierList";
import "../style/css/managementScreen.css";
import OutOfDateBatchList from "../components/product_management/OutOfDateBatchList";

class ProductManagementScreen extends Component {
  render() {
    return (
      <>
        <NavigationBar pathname="/product-management"></NavigationBar>
        <div id="management-container">
          <Tab.Container
            id="left-tabs-example"
            defaultActiveKey="first"
            transition={false}
            unmountOnExit={true}
          >
            <Row id="container-row">
              <Col variant="tabs" sm={2} id="management-tab">
                <Nav className="flex-column nav-list">
                  <Nav.Item>
                    <Nav.Link eventKey="first">Thông tin hàng hóa</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link eventKey="second">
                      Sản phẩm sắp hết hạn
                    </Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link eventKey="third">Liên hệ nhà cung cấp</Nav.Link>
                  </Nav.Item>
                </Nav>
              </Col>
              <Col sm={10} style={{padding: 0}}>
                <Tab.Content className="h-100">
                  <Tab.Pane className="h-100 overfl-auto" eventKey="first">
                    <ProductList></ProductList>
                  </Tab.Pane>
                  <Tab.Pane className="h-100" eventKey="second">
                    <OutOfDateBatchList></OutOfDateBatchList>
                  </Tab.Pane>
                  <Tab.Pane className="h-100" eventKey="third">
                    <SupplierList></SupplierList>
                  </Tab.Pane>
                </Tab.Content>
              </Col>
            </Row>
          </Tab.Container>
        </div>
      </>
    );
  }
}

export default ProductManagementScreen;
