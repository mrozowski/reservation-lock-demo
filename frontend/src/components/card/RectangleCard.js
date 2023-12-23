import React from 'react';
import './RectangleCard.css';

const RectangleCard = ({ title, price, dateTime }) => {
    return (
        <div className="rectangle-card">
            <div className="title">{title}</div>
            <div className="price">{price}</div>
            <div className="date-time">{dateTime}</div>
        </div>
    );
};

export default RectangleCard;