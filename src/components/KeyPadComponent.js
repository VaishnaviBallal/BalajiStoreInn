import React, {Component} from 'react';
import './buttonsPanel.css';


class KeyPadComponent extends Component {

    render() {
        return (
            <div className="button">
                <button name="(" onClick={e => this.props.onClick(e.target.name)}>(</button>
                <button name="CE" onClick={e => this.props.onClick(e.target.name)}>CE</button>
                <button name=")" onClick={e => this.props.onClick(e.target.name)}>)</button>
                <button name="C" onClick={e => this.props.onClick(e.target.name)}>C</button><br/>


                <button name="1" className='btn-num' onClick={e => this.props.onClick(e.target.name)}>1</button>
                <button name="2" className='btn-num'onClick={e => this.props.onClick(e.target.name)}>2</button>
                <button name="3" className='btn-num'onClick={e => this.props.onClick(e.target.name)}>3</button>
                <button name="+" className='btn-op' onClick={e => this.props.onClick(e.target.name)}>+</button><br/>


                <button name="4" className='btn-num' onClick={e => this.props.onClick(e.target.name)}>4</button>
                <button name="5" className='btn-num' onClick={e => this.props.onClick(e.target.name)}>5</button>
                <button name="6" className='btn-num' onClick={e => this.props.onClick(e.target.name)}>6</button>
                <button name="-" className='btn-op' onClick={e => this.props.onClick(e.target.name)}>-</button><br/>

                <button name="7" className='btn-num' onClick={e => this.props.onClick(e.target.name)}>7</button>
                <button name="8" className='btn-num' onClick={e => this.props.onClick(e.target.name)}>8</button>
                <button name="9" className='btn-num' onClick={e => this.props.onClick(e.target.name)}>9</button>
                <button name="*" className='btn-op' onClick={e => this.props.onClick(e.target.name)}>x</button><br/>


                <button name="." className='btn-num'onClick={e => this.props.onClick(e.target.name)}>.</button>
                <button name="0" className='btn-num' onClick={e => this.props.onClick(e.target.name)}>0</button>
                <button name="=" className='btn-op' id='equal' onClick={e => this.props.onClick(e.target.name)}>=</button>
                <button name="/" className='btn-op' onClick={e => this.props.onClick(e.target.name)}>÷</button><br/>
            </div>
        );
    }
}


export default KeyPadComponent;
