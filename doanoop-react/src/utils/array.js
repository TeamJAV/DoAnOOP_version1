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

export { isArrayNull, findIndexById };
