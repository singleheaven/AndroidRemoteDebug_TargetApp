namespace art {
namespace JDWP {
    const int PORT = 8011;
    enum JdwpTransportType {
      kJdwpTransportUnknown = 0,
      kJdwpTransportSocket = 1,       // transport=dt_socket，aosp是1
      kJdwpTransportSocket_miui = 2,       // transport=dt_socket，小米是2,
      kJdwpTransportAndroidAdb,   // transport=dt_android_adb
    };
    std::ostream& operator<<(std::ostream& os, const JdwpTransportType& rhs);

    struct JdwpOptions {
      //JdwpTransportType transport = 1;//JDWP::kJdwpTransportSocket;
      int transport = 1;//JDWP::kJdwpTransportSocket;
      bool server = true;
      bool suspend = false;
      std::string host = "";
      uint16_t port = PORT;
    };

    bool operator==(const JdwpOptions& lhs, const JdwpOptions& rhs);
}
}