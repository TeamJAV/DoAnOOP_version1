const isArrayNull = (arr) => {
  if (arr && arr.length > 0) {
    return false;
  } else {
    return true;
  }
};

const findIndexById = (id, array) => {
  const index = array.findIndex((e) => {
    return e.id === id;
  });
  return index;
};

const sortByProductName = (a, b) => {
  const textA = a.products.name.toUpperCase();
  const textB = b.products.name.toUpperCase();
  if (textA < textB) {
    return -1;
  } else if (textA > textB) {
    return 1;
  } else {
    return 0;
  }
}

export { isArrayNull, findIndexById, sortByProductName };
