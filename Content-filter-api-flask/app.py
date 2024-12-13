from flask import Flask, request, jsonify
import google.generativeai as genai

# Initialize the Generative AI model
model = genai.GenerativeModel("gemini-1.5-flash")
chat = model.start_chat(
    history=[
        {"role": "user", "parts": "Hello"},
        {"role": "model", "parts": "Great to meet you. What would you like to know?"},
    ]
)

# Flask app setup
app = Flask(__name__)

# Function to check for NSFW content
def check_nsfw(text):
    """
    Sends a message to the chat model and interprets if the text is NSFW.
    """
    try:
        response = chat.send_message(text)
        response_text = response.text
        print(f"Model Response: {response_text}")

        # Simple keyword check for NSFW indicators
        nsfw_keywords = ["inappropriate", "offensive", "explicit", "nsfw"]
        is_nsfw = any(keyword in response_text.lower() for keyword in nsfw_keywords)

        return {
            "input": text,
            "analysis": response_text,
            "is_nsfw": is_nsfw
        }
    except Exception as e:
        return {"error": str(e)}

# Flask route for NSFW analysis
@app.route('/analyze', methods=['POST'])
def analyze_text():
    """
    Endpoint to analyze text for NSFW content.
    """
    data = request.json
    if 'text' not in data:
        return jsonify({"error": "Missing 'text' field in request."}), 400

    text = data['text']
    result = check_nsfw(text)
    return jsonify(result)

# Flask app runner
if __name__ == '__main__':
    app.run(debug=True)
