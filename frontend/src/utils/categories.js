export const categories = [
  'ELECTRONICS',
  'FASHION',
  'HOME',
  'BOOKS',
  'BEAUTY',
  'SPORTS',
  'GROCERY'
];

export const formatCategory = (value) =>
  value.charAt(0) + value.slice(1).toLowerCase();
