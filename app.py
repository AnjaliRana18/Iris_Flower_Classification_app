from flask import Flask, request, jsonify
import numpy as np
import pickle
model = pickle.load(open('irismodel.pkl', 'rb'))
app = Flask(__name__)
@app.route('/')
def index():
    return "IRIS FLOWER CLASSIFICATION"
@app.route('/predict', methods=['POST'])
def predict():
    sl = request.form.get("sepal_length")
    sw = request.form.get('sepal_width')
    pl = request.form.get('petal_length')
    pw = request.form.get('petal_width')
    input_query = np.array([[sl,sw,pl,pw]])
    result = model.predict(input_query)[0]
    return jsonify({'species': str(result)})

if __name__ == '__main__':
    app.run(debug=True)