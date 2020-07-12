import React, { Component } from "react";
import { Navbar, Nav } from "react-bootstrap";
import { ReactComponent as Logo } from "../style/img/github-brands.svg";
import "../style/css/navBar.css"

export default class NavigationBar extends Component {
  render() {
    return (
      <Navbar bg="light" expand="lg" className="justify-content-center">
        <div className="mx-auto d-sm-flex d-block flex-sm-nowrap">
          <Navbar.Brand href="#home">
            <Logo style={{ width: 40, height: 40 }}></Logo>
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="mr-auto" activeKey={this.props.pathname}>
              <Nav.Link href="/import">Nhập hàng</Nav.Link>
              <Nav.Link href="/selling">Bán hàng</Nav.Link>
              <Nav.Link href="/refund">Trả hàng</Nav.Link>
              <Nav.Link href="/trans-stats">Thống kê GD</Nav.Link>
              <Nav.Link href="/product-management">Quản lý hàng</Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </div>
      </Navbar>
    );
  }
}
