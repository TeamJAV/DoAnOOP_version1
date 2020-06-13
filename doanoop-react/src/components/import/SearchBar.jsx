import React, { Component } from "react";
import { isArrayNull } from "../../utils/array";

export default class SearchBar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      value: "",
      searchResults: [],
      didSearch: false,
    };
  }

  handleInputChange = (event) => {
    const value = event.target.value;
    if (value === "") {
      this.setState({
        value: "",
        searchResults: [],
        didSearch: false,
      });
      return;
    }
    this.setState(
      {
        value: value,
      },
      () => {
        fetch(
          `http://localhost:8081/import/${this.props.type}/${this.state.value}`,
          {
            method: "GET",
          }
        )
          .then((res) => {
            return res.json();
          })
          .then((data) => {
            this.setState({
              searchResults: data,
              didSearch: true,
            });
          })
          .catch((err) => {
            console.log(err);
          });
      }
    );
  };

  handleInputOnBlur = () => {
    this.setState({
      searchResults: [],
      didSearch: false,
    });
  };

  handleSelectResult = (event) => {
    const id = event.currentTarget.dataset.id;
    const result = this.state.searchResults.find((e) => {
      return e.id === parseInt(id);
    });
    this.props.onSelect(result);
    this.setState({
      value: result.id + " - " + result.name,
    });
  };

  renderSearchResults = () => {
    if (!isArrayNull(this.state.searchResults)) {
      let list = this.state.searchResults.map((result) => {
        return (
          <div
            className="search-result align-center"
            key={result.id}
            data-id={result.id}
            onMouseDown={this.handleSelectResult}
            style={{
              padding: "0 0 0 30px",
              height: " 50px",
            }}
          >
            <p style={{ margin: "0px" }}>
              {result.id} - {result.name}
            </p>
          </div>
        );
      });
      return list;
    } else {
      if (this.state.didSearch) {
        return <p>Khong tim thay ket qua nao</p>;
      } else {
        return null;
      }
    }
  };

  render() {
    return (
      <>
        <div className={this.props.type} style={{ display: "inline-block" }}>
          <div className={`${this.props.type}__search-container`}>
            <input
              type="text"
              className={`${this.props.type}__search-bar`}
              value={this.state.value}
              onChange={this.handleInputChange}
              onBlur={this.handleInputOnBlur}
            />
          </div>
          <div className={`${this.props.type}__results`}>
            {this.renderSearchResults()}
          </div>
        </div>
      </>
    );
  }
}
