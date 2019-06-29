
Pod::Spec.new do |s|
  s.name         = "RNReactNativeAndroidSerialportToolV2"
  s.version      = "1.0.0"
  s.summary      = "RNReactNativeAndroidSerialportToolV2"
  s.description  = <<-DESC
                  RNReactNativeAndroidSerialportToolV2
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/author/RNReactNativeAndroidSerialportToolV2.git", :tag => "master" }
  s.source_files  = "RNReactNativeAndroidSerialportToolV2/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  